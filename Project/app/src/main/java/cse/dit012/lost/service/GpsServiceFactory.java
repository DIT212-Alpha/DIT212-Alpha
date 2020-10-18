package cse.dit012.lost.service;

public final class GpsServiceFactory {
    private GpsServiceFactory() {
    }

    public static GpsService createGps() {
        return new Gps();
    }
}
