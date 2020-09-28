package cse.dit012.lost;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import cse.dit012.lost.firebase.FirebaseQueryLiveData;

/**
 * Repository responsible for storing and retrieving information about broadcasts.
 */
public class BroadcastRepository {
    // Firebase database keys
    private static final String BROADCASTS_KEY = "broadcasts";
    private static final String BROADCAST_CREATEDAT_KEY = "createdAt";
    private static final String BROADCAST_LAT_KEY = "lat";
    private static final String BROADCAST_LONG_KEY = "long";
    private static final String BROADCAST_COURSECODE_KEY = "courseCode";
    private static final String BROADCAST_DESCRIPTION_KEY = "description";

    // Firebase database instance
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();

    /**
     * Retrieves an immutable live list of all currently active broadcasts.
     * @return the list of broadcasts
     */
    public LiveData<List<Broadcast>> getActiveBroadcasts() {
        // Query root node of broadcasts tree in database
        DatabaseReference reference = db.getReference(BROADCASTS_KEY);
        LiveData<DataSnapshot> query = new FirebaseQueryLiveData(reference);

        // Transform broadcasts in database to broadcasts in model
        // DataSnapshot of broadcasts root -> List<Broadcast>
        return Transformations.map(query, BroadcastRepository::deserializeBroadcastsFromDataSnapshot);
    }

    public void updateCourseDescription(String course, String description, String id){
        DatabaseReference reference = db.getReference(BROADCASTS_KEY).child(id);
        reference.child(BROADCAST_COURSECODE_KEY).setValue(course);
        reference.child(BROADCAST_DESCRIPTION_KEY).setValue(description);
    }

    /**
     * Extracts information about a single broadcast in database into a {@link Broadcast} object.
     * @param broadcastSnapshot the {@link DataSnapshot} representing a single broadcast
     * @return the corresponding {@link Course}
     */
    private static Broadcast deserializeBroadcastFromDataSnapshot(DataSnapshot broadcastSnapshot) {
        double lat = broadcastSnapshot.child(BROADCAST_LAT_KEY).getValue(double.class);
        double lon = broadcastSnapshot.child(BROADCAST_LONG_KEY).getValue(double.class);
        String course = broadcastSnapshot.child(BROADCAST_COURSECODE_KEY).getValue(String.class);
        String description = broadcastSnapshot.child(BROADCAST_DESCRIPTION_KEY).getValue(String.class);

        return new Broadcast(new Course(course), description, lat, lon);
    }

    /**
     * Extracts information about a collection of broadcasts in database into a {@link List<Broadcast>} object.
     * @param broadcastsSnapshot the {@link DataSnapshot} representing a collection of broadcasts
     * @return the corresponding {@link List<Broadcast>}
     */
    private static List<Broadcast> deserializeBroadcastsFromDataSnapshot(DataSnapshot broadcastsSnapshot) {
        List<Broadcast> broadcasts = new ArrayList<>();
        for (DataSnapshot broadcastSnapshot : broadcastsSnapshot.getChildren()) {
            broadcasts.add(deserializeBroadcastFromDataSnapshot(broadcastSnapshot));
        }
        return broadcasts;
    }
}