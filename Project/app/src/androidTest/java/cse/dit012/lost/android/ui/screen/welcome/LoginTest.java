package cse.dit012.lost.android.ui.screen.welcome;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cse.dit012.lost.R;
import cse.dit012.lost.android.ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("cse.dit012.lost", appContext.getPackageName());
    }

    @Test
    public void typeUsername() {
        onView(withId(R.id.editTextEmail)).perform(typeText("Mathias"));
    }

    @Test
    public void typePassword() {
        onView(withId(R.id.editTextPassword)).perform((typeText("hejhejhej")));
    }

    @Test
    public void clickLogin() {
        onView(withId(R.id.cirLoginButton)).perform(click());
    }

    @Test
    public void clickRegister() {
        onView(withId(R.id.cirRegisterButton)).perform(click());
    }
}
