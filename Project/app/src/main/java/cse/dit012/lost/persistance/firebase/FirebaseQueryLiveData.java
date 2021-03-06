package cse.dit012.lost.persistance.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * LiveData object encapsulating a query to the Firebase realtime database.
 * When updates happen to the query, the LiveData object is updated which notifies its observers.
 * Inspired by Firebase blog: https://firebase.googleblog.com/2017/12/using-android-architecture-components.html
 * <p>
 * Author: Benjamin Sannholm, Bashar Oumari
 * Used by: {@link FirebaseBroadcastRepository}
 */
final class FirebaseQueryLiveData extends LiveData<DataSnapshot> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";

    // The Firebase query
    private final Query query;

    // Listener called when database query updates
    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            // Data changed in Firebase database, so update value in LiveData object
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
        }
    };

    /**
     * Constructs a live data object listening to a given Firebase query.
     *
     * @param query the {@link Query} to listen to
     */
    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");

        // Someone is observing live data object for updates,
        // so start listening for updates from Firebase database query.
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");

        // No one is observing live data object anymore,
        // so stop listening for updates from Firebase database query.
        query.removeEventListener(listener);
    }
}