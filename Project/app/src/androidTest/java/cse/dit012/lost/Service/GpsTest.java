package cse.dit012.lost.service;

import android.Manifest;
import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cse.dit012.lost.android.PermissionUtil;
import cse.dit012.lost.android.ui.map.LostMapFragment;
import cse.dit012.lost.android.ui.screen.map.AddBroadcastFragment;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class GpsTest {
    @Before
    public void setUp() {
        FragmentScenario.launchInContainer(LostMapFragment.class);
    }

    @Test
    public void getLocation() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        LatLng position = Gps.getGps().getLocation(appContext);
        assertNotNull(position);
    }

}