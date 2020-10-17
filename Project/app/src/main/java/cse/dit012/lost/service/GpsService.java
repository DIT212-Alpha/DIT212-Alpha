package cse.dit012.lost.service;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import cse.dit012.lost.android.ui.map.LostMapFragment;
import cse.dit012.lost.android.ui.screen.map.AddBroadcastFragment;

/**
 * Author: Mathias Drage
 * Responsibility: Interface for Gps
 * Used by: {@link AddBroadcastFragment} and {@link LostMapFragment}
 */
public interface GpsService {
    /**
     * @return Singleton object
     */
    static GpsService getGps() {
        return new Gps();
    }

    /**
     * @param context Given by the app activity using this class by method requireContext()
     * @return current or last known location of the device running this app
     */
    LatLng getLocation(Context context);
}
