package cse.dit012.lost.persistance.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cse.dit012.lost.BroadcastRepositoryProvider;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;
import java9.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Firebase backed implementation of repository responsible for storing and retrieving information about broadcasts.
 * <p>
 * Author: Benjamin Sannholm, Bashar Oumari, Mathias Drage
 * Uses: {@link MapCoordinates}, {@link Broadcast}, {@link BroadcastId}, {@link BroadcastRepository},
 * {@link CourseCode}, {@link UserId}, {@link CurrentDateLiveData}, {@link FirebaseQueryLiveData}
 * Used by: {@link BroadcastRepositoryProvider}
 */
public final class FirebaseBroadcastRepository implements BroadcastRepository {
    // Firebase database keys
    private static final String BROADCASTS_KEY = "broadcasts";
    private static final String BROADCAST_OWNER_KEY = "ownerUID";
    private static final String BROADCAST_CREATEDAT_KEY = "createdAt";
    private static final String BROADCAST_LASTACTIVE_KEY = "lastActive";
    private static final String BROADCAST_LAT_KEY = "lat";
    private static final String BROADCAST_LONG_KEY = "long";
    private static final String BROADCAST_COURSECODE_KEY = "courseCode";
    private static final String BROADCAST_DESCRIPTION_KEY = "description";

    // Firebase database instance
    private final FirebaseDatabase db;

    /**
     * Creates a new broadcast repository backed by the given Firebase database instance.
     *
     * @param firebase the {@link FirebaseDatabase} instance to use for the repository
     */
    public FirebaseBroadcastRepository(FirebaseDatabase firebase) {
        db = checkNotNull(firebase);
    }

    /**
     * Constructs a database reference for the given broadcast id in the Firebase database.
     *
     * @param id the {@link BroadcastId} to use in the path of the {@link DatabaseReference}
     * @return a {@link DatabaseReference} to the root of the given {@link BroadcastId}
     */
    private DatabaseReference getBroadcastReference(BroadcastId id) {
        return db.getReference(BROADCASTS_KEY).child(id.toString());
    }

    @Override
    public BroadcastId nextIdentity() {
        // Generate a random UUID each time a new BroadcastId is requested
        return new BroadcastId(UUID.randomUUID().toString().toLowerCase());
    }

    @Override
    public CompletableFuture<Broadcast> store(Broadcast broadcast) {
        CompletableFuture<Broadcast> future = new CompletableFuture<>();

        // Perform transaction to save all properties of broadcast atomically
        getBroadcastReference(broadcast.getId()).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                currentData.child(BROADCAST_OWNER_KEY).setValue(broadcast.getOwnerUID().toString());
                currentData.child(BROADCAST_CREATEDAT_KEY).setValue(broadcast.getCreatedAt().getTime() / 1000);
                currentData.child(BROADCAST_LASTACTIVE_KEY).setValue(broadcast.getLastActive().getTime() / 1000);
                currentData.child(BROADCAST_LAT_KEY).setValue(broadcast.getCoordinates().getLatitude());
                currentData.child(BROADCAST_LONG_KEY).setValue(broadcast.getCoordinates().getLongitude());
                currentData.child(BROADCAST_COURSECODE_KEY).setValue(broadcast.getCourse().toString());
                currentData.child(BROADCAST_DESCRIPTION_KEY).setValue(broadcast.getDescription());
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                if (committed) {
                    future.complete(deserializeBroadcastFromDataSnapshot(currentData));
                } else {
                    future.completeExceptionally(error.toException());
                }
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<Broadcast> getById(BroadcastId id) {
        CompletableFuture<Broadcast> future = new CompletableFuture<>();

        getBroadcastReference(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    future.complete(deserializeBroadcastFromDataSnapshot(snapshot));
                } else {
                    future.completeExceptionally(new RuntimeException("Broadcast with id " + id + " does not exist"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }

    @Override
    public LiveData<Broadcast> observeById(BroadcastId id) {
        // Query root of desired broadcast
        Query query = getBroadcastReference(id);
        LiveData<DataSnapshot> queryLiveData = new FirebaseQueryLiveData(query);

        // Transform a single broadcast in database to Broadcast in model
        // DataSnapshot of a broadcast -> Broadcast
        return Transformations.map(queryLiveData, this::deserializeBroadcastFromDataSnapshot);
    }

    @Override
    public LiveData<List<Broadcast>> observeActiveBroadcasts() {
        // Query root node of broadcasts tree
        long oldestActiveTime = (System.currentTimeMillis() / 1000) - Broadcast.ACTIVE_TIME_MARGIN_SECONDS;
        Query query = db.getReference(BROADCASTS_KEY)
                .orderByChild(BROADCAST_LASTACTIVE_KEY)
                .startAt(oldestActiveTime); // Only retrieve active broadcasts
        LiveData<DataSnapshot> queryLiveData = new FirebaseQueryLiveData(query);

        // Transform broadcasts in database to broadcasts in model
        // DataSnapshot of broadcasts root -> List<Broadcast>
        LiveData<List<Broadcast>> recentBroadcasts = Transformations.map(queryLiveData, this::deserializeBroadcastsFromDataSnapshot);
        // Refresh inactive broadcasts every 20 seconds
        LiveData<Date> currentTime = new CurrentDateLiveData(1000 * 20);

        // Filter broadcasts to only keep active broadcasts and
        // make sure this is kept up-to-date as the current time changes
        MediatorLiveData<List<Broadcast>> activeBroadcasts = new MediatorLiveData<>();
        Observer<Object> onInputsChanged = unused -> {
            // Only propagate changes to broadcast list when both current time and broadcasts list is available
            if (recentBroadcasts.getValue() != null && currentTime.getValue() != null) {
                activeBroadcasts.setValue(filterActiveBroadcasts(recentBroadcasts.getValue(), currentTime.getValue()));
            }
        };
        // Trigger onInputsChanged either when list of broadcasts or current time updates
        activeBroadcasts.addSource(recentBroadcasts, onInputsChanged);
        activeBroadcasts.addSource(currentTime, onInputsChanged);
        return activeBroadcasts;
    }

    /**
     * Takes a list of broadcasts, the current time and gives back the list of broadcasts
     * where inactive broadcasts are filtered away based on the current time.
     *
     * @param broadcasts  the list of broadcasts
     * @param currentTime the current time
     * @return a filtered list containing only active broadcasts
     */
    private static List<Broadcast> filterActiveBroadcasts(List<Broadcast> broadcasts, Date currentTime) {
        List<Broadcast> filteredBroadcasts = new ArrayList<>(broadcasts.size());
        for (Broadcast broadcast : broadcasts) {
            if (broadcast.isActive(currentTime)) {
                filteredBroadcasts.add(broadcast);
            }
        }
        return filteredBroadcasts;
    }

    /**
     * Extracts information about a single broadcast in database into a {@link Broadcast} object.
     *
     * @param broadcastSnapshot a {@link DataSnapshot} representing a single broadcast
     * @return the corresponding {@link Broadcast}
     */
    private Broadcast deserializeBroadcastFromDataSnapshot(DataSnapshot broadcastSnapshot) {
        checkArgument(broadcastSnapshot.exists(), "Broadcast with id " + broadcastSnapshot.getKey() + " does not exist");

        String id = broadcastSnapshot.getKey();
        String ownerUID = broadcastSnapshot.child(BROADCAST_OWNER_KEY).getValue(String.class);
        long createdAt = broadcastSnapshot.child(BROADCAST_CREATEDAT_KEY).getValue(long.class);
        long lastActive = broadcastSnapshot.child(BROADCAST_LASTACTIVE_KEY).getValue(long.class);
        double lat = broadcastSnapshot.child(BROADCAST_LAT_KEY).getValue(double.class);
        double lon = broadcastSnapshot.child(BROADCAST_LONG_KEY).getValue(double.class);
        String course = broadcastSnapshot.child(BROADCAST_COURSECODE_KEY).getValue(String.class);
        String description = broadcastSnapshot.child(BROADCAST_DESCRIPTION_KEY).getValue(String.class);

        return new Broadcast(
                new BroadcastId(id),
                new UserId(ownerUID),
                new Date(createdAt * 1000),
                new Date(lastActive * 1000),
                new MapCoordinates(lat, lon),
                new CourseCode(course),
                description
        );
    }

    /**
     * Extracts information about a collection of broadcasts in database into a {@link List<Broadcast>} object.
     *
     * @param broadcastsSnapshot a {@link DataSnapshot} representing a collection of broadcasts
     * @return the corresponding {@link List<Broadcast>}
     */
    private List<Broadcast> deserializeBroadcastsFromDataSnapshot(DataSnapshot broadcastsSnapshot) {
        List<Broadcast> broadcasts = new ArrayList<>();
        for (DataSnapshot broadcastSnapshot : broadcastsSnapshot.getChildren()) {
            broadcasts.add(deserializeBroadcastFromDataSnapshot(broadcastSnapshot));
        }
        return broadcasts;
    }
}