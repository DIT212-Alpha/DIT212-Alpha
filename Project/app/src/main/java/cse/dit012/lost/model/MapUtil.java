package cse.dit012.lost.model;

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

    private MapUtil() {
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