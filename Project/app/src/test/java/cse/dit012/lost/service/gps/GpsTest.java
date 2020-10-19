package cse.dit012.lost.service.gps;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GpsTest {
    /**
     * Test if the Singleton object is returned
     */
    @Test
    public void getGps() {
        assertNotNull(GpsService.gps);
    }
}