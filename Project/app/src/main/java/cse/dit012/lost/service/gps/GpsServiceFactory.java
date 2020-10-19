package cse.dit012.lost.service.gps;

final class GpsServiceFactory {
    private GpsServiceFactory() {
    }

    public static GpsService createGps() {
        return new Gps();
    }
}
