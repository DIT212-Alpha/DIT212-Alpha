package cse.dit012.lost.service.broadcast;

import cse.dit012.lost.BroadcastRepositoryProvider;

final class BroadcastServiceFactory {
    /**
     * Gives an instance of the broadcast service.
     *
     * @return an instance of the {@link BroadcastService}
     */
    public static BroadcastService get() {
        return new BroadcastServiceImpl(BroadcastRepositoryProvider.get());
    }
}
