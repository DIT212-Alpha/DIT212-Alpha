package cse.dit012.lost.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

import cse.dit012.lost.android.ui.map.LostMapFragment;
import cse.dit012.lost.android.ui.screen.map.AddBroadcastFragment;

/**
 * Provides the GPS location of the Android device.
 * Author: Mathias Drage
 * Used by: {@link AddBroadcastFragment} and {@link LostMapFragment}
 */
public final class Gps {
    private LatLng location;
    // Communicates with the android system to manage location
    private LocationManager locationManager;
    // Singleton object
    private static Gps gps = new Gps();

    private Gps() {
    }

    /**
     * Precondition: Fragment-Context which can not be null (use requireContext(), not getContext(),
     * Must check permission wherever the method is called, as "@SuppressLint("MissingPermission")" skips permission check.
     *
     * @param context provided by a "Fragment" from the "requireContext()" method
     *                needed by the LocationManager to communicate with android
     * @return returns the latest location, might be null if no location yet have been found
     */
    @SuppressLint("MissingPermission")
    public LatLng getLocation(Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location temp = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (temp != null) {
            location = new LatLng(temp.getLatitude(), temp.getLongitude());
        }
        return location;
    }

    /**
     * Returns the singleton object
     */
    public static Gps getGps() {
        return gps;
    }
}