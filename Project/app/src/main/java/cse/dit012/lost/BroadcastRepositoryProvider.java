package cse.dit012.lost;

import com.google.firebase.database.FirebaseDatabase;

import cse.dit012.lost.android.service.ActiveBroadcastService;
import cse.dit012.lost.android.ui.screen.map.MapViewModel;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.persistance.firebase.FirebaseBroadcastRepository;

/**
 * Provides an instance of the broadcast repository.
 * <p>
 * Author: Benjamin Sannholm, Mathias Drage
 * Uses: {@link BroadcastRepository}, {@link FirebaseBroadcastRepository}
 * Used by: {@link ActiveBroadcastService}, BroadcastServiceFactory, {@link MapViewModel}
 */
public final class BroadcastRepositoryProvider {
    private BroadcastRepositoryProvider() {
    }

    /**
     * Singleton instance of broadcast repository
     */
    private static final BroadcastRepository INSTANCE = new FirebaseBroadcastRepository(FirebaseDatabase.getInstance());

    /**
     * Gives an instance of the broadcast repository.
     *
     * @return an instance of the {@link BroadcastRepository}
     */
    public static BroadcastRepository get() {
        return INSTANCE;
    }
}
