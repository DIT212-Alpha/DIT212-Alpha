package cse.dit012.lost;

import com.google.firebase.database.FirebaseDatabase;

import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.persistance.firebase.FirebaseBroadcastRepository;

public final class BroadcastRepositoryProvider {
    private BroadcastRepositoryProvider() {
    }

    private static final BroadcastRepository INSTANCE = new FirebaseBroadcastRepository(FirebaseDatabase.getInstance());

    public static BroadcastRepository get() {
        return INSTANCE;
    }
}
