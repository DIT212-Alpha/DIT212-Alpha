package cse.dit012.lost.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

/**
 * Representation of the lat and long coordinates of a point on the map.
 * Author: Benjamin Sannholm
 */
public final class MapCoordinates {
    private final double latitude;
    private final double longitude;

    /**
     * Creates a new pair of coordinates given a longitude and latitude.
     *
     * @param latitude  the longitude
     * @param longitude the latitude
     */
    public MapCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return the latitude part of the coordinate pair
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude part of the coordinate pair
     */
    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapCoordinates that = (MapCoordinates) o;
        return Double.compare(that.getLatitude(), getLatitude()) == 0 &&
                Double.compare(that.getLongitude(), getLongitude()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("latitude", latitude)
                .add("longitude", longitude)
                .toString();
    }

    /**
     * Calculates the distance between two points on a sphere.
     * Inspired by: https://stackoverflow.com/a/27943
     *
     * @param c2 coordinates of the second point
     * @return the distance between the points in meters
     */
    public double distanceTo(MapCoordinates c2) {
        double lat1 = getLatitude(), lon1 = getLongitude();
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