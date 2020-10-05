package cse.dit012.lost.model;

import cse.dit012.lost.model.broadcast.Broadcast;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

/**
 * Utility with methods relating to the map, its contents and geography.
 * Author: Benjamin Sannholm
 */
public final class MapUtil {
    // How far away the owner of a broadcast may move and still have the broadcast be considered active.
    private static final double BROADCAST_ACTIVATION_RADIUS_METERS = 20;

    private MapUtil() {
    }

    /**
     * Checks if a point is within range to keep the given broadcast alive.
     *
     * @param coords    the coordinates of the point
     * @param broadcast the broadcast
     * @return true, if it is in range, false otherwise
     */
    public static boolean isPointInRangeOfBroadcast(MapCoordinates coords, Broadcast broadcast) {
        return distanceBetweenPoints(broadcast.getCoordinates(), coords) <= BROADCAST_ACTIVATION_RADIUS_METERS;
    }

    /**
     * Calculates the distance between two points on a sphere.
     * Inspired by: https://stackoverflow.com/a/27943
     *
     * @param c1 coordinates of the first point
     * @param c2 coordinates of the second point
     * @return the distance between the points in meters
     */
    public static double distanceBetweenPoints(MapCoordinates c1, MapCoordinates c2) {
        double lat1 = c1.getLatitude(), lon1 = c1.getLongitude();
        double lat2 = c2.getLatitude(), lon2 = c2.getLongitude();

        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);
        double a = sin(dLat / 2) * sin(dLat / 2)
                + cos(toRadians(lat1)) * cos(toRadians(lat2)) * sin(dLon / 2) * sin(dLon / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        double R = 6371 * 1000; // Radius of the earth in meters
        double d = R * c; // Distance in meters

        return d;
    }
}