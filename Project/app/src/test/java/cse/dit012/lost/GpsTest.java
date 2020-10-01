package cse.dit012.lost;

import org.junit.Test;

import cse.dit012.lost.service.Gps;

import static org.junit.Assert.*;

public class GpsTest {

    @Test
    public void getGps() {
        Gps gps1 = Gps.getGps();
        Gps gps2 = Gps.getGps();
        assertEquals(gps1,gps2);
    }
}