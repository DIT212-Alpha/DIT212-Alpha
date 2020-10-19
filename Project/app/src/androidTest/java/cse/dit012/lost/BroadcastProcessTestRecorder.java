package cse.dit012.lost;


import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cse.dit012.lost.R;
import cse.dit012.lost.android.ui.MainActivity;
import cse.dit012.lost.service.LoginService;
import cse.dit012.lost.service.LoginServiceFactory;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BroadcastProcessTestRecorder {

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    /**
     * This test shows a process on how to log in and create a broadcast. Also how to edit the broadcast.
     * See the emulator on how it tests.
     */


    public static ViewAction clickPercent(final float pctX, final float pctY){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates(View view) {

                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);
                        int w = view.getWidth();
                        int h = view.getHeight();

                        float x = w * pctX;
                        float y = h * pctY;

                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                },
                Press.FINGER);
    }

    @Test
    public void broadcastInfoWindowFragmentTestRecorder() throws ExecutionException, InterruptedException, TimeoutException {
        LoginService login = LoginServiceFactory.createEmailAndPasswordService("test@test.com", "test123");
        login.login().get(5, TimeUnit.SECONDS);

        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.broadcast_btn))
                .perform(click());

        onView(withId(R.id.descriptionEdittext))
                .perform(replaceText("test"), closeSoftKeyboard());

        onView(withId(R.id.addBtn))
                .perform(click());

        Thread.sleep(500);

        onView(withId(R.id.map))
                .perform(clickPercent(0.5f, 0.5f));

        Thread.sleep(500);

        onView(withId(R.id.editInfoWindowButton))
                .perform(click());

        onView(withId(R.id.editCourseText))
                .perform(replaceText("DIT123"));

        onView(withId(R.id.editCourseText))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.cancelInfoWindowButton))
                .perform(click());

        onView(withId(R.id.editInfoWindowButton))
                .perform(click());

        onView(withId(R.id.editCourseText))
                .perform(replaceText("DIT123"));

        onView(withId(R.id.editCourseText))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.saveInfoWindowButton))
                .perform(click());

        onView(withId(R.id.editInfoWindowButton))
                .perform(click());

        onView(withId(R.id.editDescriptionText))
                .perform(click());

        onView(withId(R.id.editDescriptionText))
                .perform(replaceText("test123"));

        onView(withId(R.id.editDescriptionText))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.cancelInfoWindowButton))
                .perform(click());

        onView(withId(R.id.editInfoWindowButton))
                .perform(click());

        onView(withId(R.id.editDescriptionText))
                .perform(click());

        onView(withId(R.id.editDescriptionText))
                .perform(replaceText("test123"));

        onView(withId(R.id.editDescriptionText))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.saveInfoWindowButton))
                .perform(click());

        onView(withId(R.id.delete))
                .perform(click());

        onView(withId(R.id.delete))
                .perform(click());

        onView(withId(R.id.sign_out_btn))
                .perform(click());
    }
}
