package cse.dit012.lost.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @Author: Sophia Pham
 */

public class MapCoordinatesTest {
    double latitude = 1;
    double longitude = 1;
    MapCoordinates mapCoordinates = new MapCoordinates(latitude, longitude);
    MapCoordinates mapCoordinates2 = new MapCoordinates(2, 2);

    @Test
    public void getLatitude() {
        assertEquals(mapCoordinates.getLatitude(), latitude, 0);
    }

    @Test
    public void getLongitude() {
        assertEquals(mapCoordinates.getLongitude(), longitude, 0);
    }

    @Test
    public void equalsTest() {
        assertEquals(new MapCoordinates(latitude, longitude), mapCoordinates);
        assertNotEquals(mapCoordinates, mapCoordinates2);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(new MapCoordinates(latitude, longitude).hashCode(), mapCoordinates.hashCode());
        assertNotEquals(mapCoordinates, mapCoordinates2);
    }

    @Test
    public void toStringTest() {
        assertEquals(new MapCoordinates(latitude, longitude).toString(), mapCoordinates.toString());
    }

    @Test
    public void distanceTo() {
        double meters = mapCoordinates.distanceTo(mapCoordinates);
        assertEquals(0, meters, 0.5);
    }

}
