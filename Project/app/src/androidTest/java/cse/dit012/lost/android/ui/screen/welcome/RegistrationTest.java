package cse.dit012.lost.android.ui.screen.welcome;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
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
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RegistrationTest {
    @Before
    public void setUp() {
        FragmentScenario.launchInContainer(RegistrationScreenFragment.class);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("cse.dit012.lost", appContext.getPackageName());
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
