package cse.dit012.lost.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * This class represent the lat and long coordinates of a point on the map
 * AUTHOR: Benjamin Sannholm
 */

public final class MapCoordinates {
    private final double latitude;
    private final double longitude;

    public MapCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

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
}
