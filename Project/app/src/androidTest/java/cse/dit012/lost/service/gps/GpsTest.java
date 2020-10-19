package cse.dit012.lost.service.gps;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cse.dit012.lost.android.ui.map.LostMapFragment;
import cse.dit012.lost.model.MapCoordinates;

import static org.junit.Assert.assertNotNull;

/**
 * Tests if getLocation returns a valid location (not null)
 */
@RunWith(AndroidJUnit4.class)
public class GpsTest {
    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant("android.permission.ACCESS_FINE_LOCATION");

    /**
     * The app jumps to an appropriate fragment where the getLocation can be called
     */
    @Before
    public void setUp() {
        FragmentScenario.launchInContainer(LostMapFragment.class);
    }

    /**
     * Test the GpsService getLocation() method
     */
    @Test
    public void getLocation() {
        Context appContext = ApplicationProvider.getApplicationContext();
        MapCoordinates position = GpsService.gps.getLocation(appContext);
        assertNotNull(position); // TODO: Return value can be null!
    }
}