package cse.dit012.lost.android.ui.screen.welcome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cse.dit012.lost.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RegistrationTest {
    @Before
    public void setUp() {
        // Create a TestNavHostController
        TestNavHostController navController = new TestNavHostController(ApplicationProvider.getApplicationContext());
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            navController.setGraph(R.navigation.nav_graph);
            navController.setCurrentDestination(R.id.registerFragment);
        });

        // Launch fragment with nav controller overridden
        FragmentScenario.launchInContainer(RegistrationScreenFragment.class,
                null,
                new FragmentFactory() {
                    @NonNull
                    @Override
                    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                        RegistrationScreenFragment fragment = new RegistrationScreenFragment();
                        fragment.getViewLifecycleOwnerLiveData().observeForever(viewLifecycleOwner -> {
                            if (viewLifecycleOwner != null) {
                                Navigation.setViewNavController(fragment.requireView(), navController);
                            }
                        });
                        return fragment;
                    }
                });
    }

    @Test
    public void typeEmail() {
        onView(withId(R.id.registerTextEmail)).perform(typeText("test@test.com"));
    }

    @Test
    public void typeName() {
        onView(withId(R.id.registerTextUserName)).perform(typeText("test"));
    }

    @Test
    public void typeSurName() {
        onView(withId(R.id.registerTextSurName)).perform(typeText("testson"));
    }

    @Test
    public void typePw() {
        onView(withId(R.id.registerTextPassword)).perform(typeText("hejhejhej"));
    }

    @Test
    public void clickRegister() {
        onView(withId(R.id.cirRegisterButton)).perform(click());
    }
}
