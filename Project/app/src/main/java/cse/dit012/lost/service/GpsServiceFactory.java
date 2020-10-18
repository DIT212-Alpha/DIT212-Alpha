package cse.dit012.lost.service;

public final class GpsServiceFactory {
    public static GpsService createGps(){
        return new Gps();
    }
}
