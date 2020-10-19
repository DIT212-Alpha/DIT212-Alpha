package cse.dit012.lost.service.gps;

import android.content.Context;

import cse.dit012.lost.android.service.ActiveBroadcastService;
import cse.dit012.lost.android.ui.map.LostMapFragment;
import cse.dit012.lost.android.ui.screen.map.AddBroadcastFragment;
import cse.dit012.lost.model.MapCoordinates;

/**
 * Service for retrieving current location of user.
 * <p>
 * Author: Mathias Drage
 * Uses: {@link GpsServiceFactory}, {@link MapCoordinates}
 * Used by: {@link ActiveBroadcastService}, {@link AddBroadcastFragment}, {@link Gps},
 * {@link GpsServiceFactory}, {@link LostMapFragment}
 */
public interface GpsService {
    /**
     * @return Singleton object
     */
    GpsService gps = GpsServiceFactory.createGps();

    /**
     * Retrieves the user's current or last known location.
     *
     * @param context Given by the app activity using this class by method requireContext()
     * @return current or last known location of the device running this app
     * or null if no location was found yet
     */
    MapCoordinates getLocation(Context context);
}