package cse.dit012.lost.service.gps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

import cse.dit012.lost.model.MapCoordinates;

/**
 * Provides the GPS location of the Android device.
 * <p>
 * Author: Mathias Drage
 * Uses: {@link GpsService}, {@link MapCoordinates}
 * Used by: {@link GpsServiceFactory}
 */
final class Gps implements GpsService {
    private LatLng lastKnownLocation;

    @Override
    @SuppressLint("MissingPermission")
    public MapCoordinates getLocation(Context context) {
        // Communicates with the android system to manage location
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location temp = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (temp != null) {
            lastKnownLocation = new LatLng(temp.getLatitude(), temp.getLongitude());
        }
        if (lastKnownLocation == null) {
            return null;
        }
        return new MapCoordinates(lastKnownLocation.latitude, lastKnownLocation.longitude);
    }
}