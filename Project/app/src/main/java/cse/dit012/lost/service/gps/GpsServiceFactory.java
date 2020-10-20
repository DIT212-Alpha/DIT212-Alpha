package cse.dit012.lost.service.gps;

/**
 * Factory for constructing a {@link GpsService}.
 * <p>
 * Author: Mathias Drage
 * Uses: {@link GpsService}, {@link Gps}
 * Used by: {@link GpsService}
 */
final class GpsServiceFactory {
    private GpsServiceFactory() {
    }

    /**
     * Gives an instance of the GPS service.
     *
     * @return an instance of the {@link GpsService}
     */
    public static GpsService createGps() {
        return new Gps();
    }
}
