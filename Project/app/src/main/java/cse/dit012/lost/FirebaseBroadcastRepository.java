package cse.dit012.lost;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import cse.dit012.lost.firebase.FirebaseQueryLiveData;

/**
 * Repository responsible for storing and retrieving information about broadcasts.
 */
public class FirebaseBroadcastRepository implements BroadcastRepository {
    // Firebase database keys
    private static final String BROADCASTS_KEY = "broadcasts";
    private static final String BROADCAST_CREATEDAT_KEY = "createdAt";
    private static final String BROADCAST_LASTACTIVE_KEY = "lastActive";
    private static final String BROADCAST_LAT_KEY = "lat";
    private static final String BROADCAST_LONG_KEY = "long";
    private static final String BROADCAST_COURSECODE_KEY = "courseCode";
    private static final String BROADCAST_DESCRIPTION_KEY = "description";

    public static final long ACTIVE_TIME_MARGIN_SECONDS = 60;

    // Firebase database instance
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    public LiveData<List<Broadcast>> getActiveBroadcasts() {
        // Query root node of broadcasts tree
        long oldestActiveTime = (System.currentTimeMillis() / 1000) - ACTIVE_TIME_MARGIN_SECONDS;
        Query query = db.getReference(BROADCASTS_KEY)
                .orderByChild(BROADCAST_LASTACTIVE_KEY)
                .startAt(oldestActiveTime); // Only retrieve active broadcasts
        LiveData<DataSnapshot> queryLiveData = new FirebaseQueryLiveData(query);

        // Transform broadcasts in database to broadcasts in model
        // DataSnapshot of broadcasts root -> List<Broadcast>
        LiveData<List<Broadcast>> recentBroadcasts = Transformations.map(queryLiveData, this::deserializeBroadcastsFromDataSnapshot);
        LiveData<Date> currentTime = new CurrentDateLiveData();

        MediatorLiveData<List<Broadcast>> activeBroadcasts = new MediatorLiveData<>();
        activeBroadcasts.addSource(recentBroadcasts, broadcasts -> {
            activeBroadcasts.setValue(FirebaseBroadcastRepository.filterBroadcasts(recentBroadcasts, currentTime));
        });
        activeBroadcasts.addSource(currentTime, broadcasts -> {
            activeBroadcasts.setValue(FirebaseBroadcastRepository.filterBroadcasts(recentBroadcasts, currentTime));
        });
        return Transformations.distinctUntilChanged(recentBroadcasts);
    }

    private static List<Broadcast> filterBroadcasts(LiveData<List<Broadcast>> broadcasts, LiveData<Date> currentTime) {
        if (broadcasts.getValue() == null || currentTime.getValue() == null) {
            return Collections.emptyList();
        }

        List<Broadcast> filteredBroadcasts = new ArrayList<>(broadcasts.getValue().size());
        for (Broadcast broadcast : broadcasts.getValue()) {
            long ageSinceLastActive = (currentTime.getValue().getTime() - broadcast.getLastActive().getTime()) / 1000;
            if (ageSinceLastActive <= ACTIVE_TIME_MARGIN_SECONDS) {
                filteredBroadcasts.add(broadcast);
            }
        }
        return filteredBroadcasts;
    }

    /**
     * Extracts information about a single broadcast in database into a {@link Broadcast} object.
     * @param broadcastSnapshot the {@link DataSnapshot} representing a single broadcast
     * @return the corresponding {@link Course}
     */
    private Broadcast deserializeBroadcastFromDataSnapshot(DataSnapshot broadcastSnapshot) {
        String id = broadcastSnapshot.getKey();
        long lastActive = broadcastSnapshot.child(BROADCAST_LASTACTIVE_KEY).getValue(long.class);
        double lat = broadcastSnapshot.child(BROADCAST_LAT_KEY).getValue(double.class);
        double lon = broadcastSnapshot.child(BROADCAST_LONG_KEY).getValue(double.class);
        String course = broadcastSnapshot.child(BROADCAST_COURSECODE_KEY).getValue(String.class);
        String description = broadcastSnapshot.child(BROADCAST_DESCRIPTION_KEY).getValue(String.class);

        return new Broadcast(this,
                id,
                new Date(lastActive * 1000),
                lat,
                lon,
                new Course(course),
                description);
    }

    /**
     * Extracts information about a collection of broadcasts in database into a {@link List<Broadcast>} object.
     * @param broadcastsSnapshot the {@link DataSnapshot} representing a collection of broadcasts
     * @return the corresponding {@link List<Broadcast>}
     */
    private List<Broadcast> deserializeBroadcastsFromDataSnapshot(DataSnapshot broadcastsSnapshot) {
        List<Broadcast> broadcasts = new ArrayList<>();
        for (DataSnapshot broadcastSnapshot : broadcastsSnapshot.getChildren()) {
            broadcasts.add(deserializeBroadcastFromDataSnapshot(broadcastSnapshot));
        }
        return broadcasts;
    }

    @Override
    public Broadcast createBroadcast(double latitude, double longitude, BroadcastObject course, String description) {
        // TODO: Implement
        throw new java.lang.UnsupportedOperationException("Not yet implemented!");
    }
}