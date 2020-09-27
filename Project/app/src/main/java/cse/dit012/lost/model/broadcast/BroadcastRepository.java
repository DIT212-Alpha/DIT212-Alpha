package cse.dit012.lost.model.broadcast;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import cse.dit012.lost.persistance.firebase.FirebaseBroadcastRepository;
import java9.util.concurrent.CompletableFuture;

/**
 * Repository responsible for storing and retrieving information about broadcasts.
 */
public interface BroadcastRepository {
    static BroadcastRepository create() {
        return new FirebaseBroadcastRepository(FirebaseDatabase.getInstance());
    }

    BroadcastId nextIdentity();

    CompletableFuture<Broadcast> getById(BroadcastId broadcast);

    void store(Broadcast broadcast);

    /**
     * Retrieves a live list of all currently active broadcasts.
     * @return the list of broadcasts
     */
    LiveData<List<Broadcast>> getActiveBroadcasts();
}