package cse.dit012.lost;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import cse.dit012.lost.firebase.FirebaseQueryLiveData;

public class BroadcastRepository {
    private static final String BROADCASTS_KEY = "broadcasts";
    private static final String BROADCAST_CREATEDAT_KEY = "createdAt";
    private static final String BROADCAST_LAT_KEY = "lat";
    private static final String BROADCAST_LONG_KEY = "long";
    private static final String BROADCAST_COURSECODE_KEY = "courseCode";
    private static final String BROADCAST_DESCRIPTION_KEY = "description";

    private final FirebaseDatabase db = FirebaseDatabase.getInstance();

    public LiveData<List<Broadcast>> getActiveBroadcasts() {
        DatabaseReference reference = db.getReference(BROADCASTS_KEY);

        LiveData<DataSnapshot> query = new FirebaseQueryLiveData(reference);
        return Transformations.map(query, broadcastsSnapshot -> {
            List<Broadcast> broadcasts = new ArrayList<>();
            for (DataSnapshot snapshot : broadcastsSnapshot.getChildren()) {
                double lat = snapshot.child(BROADCAST_LAT_KEY).getValue(double.class);
                double lon = snapshot.child(BROADCAST_LONG_KEY).getValue(double.class);
                String course = snapshot.child(BROADCAST_COURSECODE_KEY).getValue(String.class);
                String description = snapshot.child(BROADCAST_DESCRIPTION_KEY).getValue(String.class);

                Broadcast broadcast = new Broadcast(new Course(course), description, lat, lon);
                broadcasts.add(broadcast);
            }
            return broadcasts;
        });
    }


}