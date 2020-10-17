package cse.dit012.lost.service;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cse.dit012.lost.android.ui.map.LostMapFragment;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UserInfoServiceTest {
    @Before
    public void setUp() {
        FragmentScenario.launchInContainer(LostMapFragment.class);
    }

    Task<AuthResult> user = FirebaseAuth.getInstance().signInWithEmailAndPassword("test@test.com", "test123");

    @Test
    public void getEmailTest() {
        assertEquals(UserInfoService.getUserInfoService().getEmail(), "test@test.com");
    }

    @Test
    public void getIDTest() {
        assertEquals(UserInfoService.getUserInfoService().getID(), "zqy2GeW5GNNHSLUCEbl53dYe5zD2");
    }
}
