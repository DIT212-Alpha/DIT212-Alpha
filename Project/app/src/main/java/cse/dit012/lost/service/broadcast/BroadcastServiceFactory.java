package cse.dit012.lost.service.broadcast;

import cse.dit012.lost.BroadcastRepositoryProvider;
import cse.dit012.lost.service.authenticateduser.AuthenticatedUserService;

/**
 * Factory for constructing an {@link AuthenticatedUserService}.
 * <p>
 * Author: Benjamin Sannholm
 * Uses: {@link BroadcastService}, {@link BroadcastServiceImpl}, {@link BroadcastRepositoryProvider}
 * Used by: {@link BroadcastService}
 */
final class BroadcastServiceFactory {
    private BroadcastServiceFactory() {
    }

    /**
     * Gives an instance of the broadcast service.
     *
     * @return an instance of the {@link BroadcastService}
     */
    public static BroadcastService get() {
        return new BroadcastServiceImpl(BroadcastRepositoryProvider.get());
    }
}
