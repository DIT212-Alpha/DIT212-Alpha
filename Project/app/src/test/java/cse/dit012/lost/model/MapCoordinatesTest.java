package cse.dit012.lost.model;

import org.junit.Test;

import static java.lang.Math.max;
import static org.junit.Assert.assertEquals;

public class MapCoordinatesTest {
    double latitude = 1;
    double longitude = 1;
    MapCoordinates mapCoordinates = new MapCoordinates(latitude, longitude);

    @Test
    public void getLatitude(){
        assertEquals(mapCoordinates.getLatitude(), latitude, 0);
    }

    @Test
    public void getLongitude() {
        assertEquals(mapCoordinates.getLongitude(), longitude, 0);
    }

    @Test
    public void equalsTest() {
        assertEquals(new MapCoordinates(latitude, longitude), mapCoordinates);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(new MapCoordinates(latitude, longitude).hashCode(), mapCoordinates.hashCode());
    }

    @Test
    public void toStringTest(){
        assertEquals(new MapCoordinates(latitude, longitude).toString(), mapCoordinates.toString());
    }
}
