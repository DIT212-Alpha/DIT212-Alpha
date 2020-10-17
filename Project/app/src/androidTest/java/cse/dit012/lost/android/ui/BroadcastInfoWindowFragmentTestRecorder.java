package cse.dit012.lost.android.ui;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import cse.dit012.lost.R;
import cse.dit012.lost.service.MailAndPasswordLoginService;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BroadcastInfoWindowFragmentTestRecorder {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    /**
     * This test shows a process on how to log in and create a broadcast. Also how to edit the broadcast.
     * See the emulator on how it tests.
     */

    @Test
    public void broadcastInfoWindowFragmentTestRecorder() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        MailAndPasswordLoginService login = new MailAndPasswordLoginService();
        login.userSignIn("test@test.com", "test123", success -> {
            completableFuture.complete(null);
        });
        completableFuture.get();

        onView(withId(R.id.broadcast_btn))
                .perform(click());

        onView(withId(R.id.descriptionEdittext))
                .perform(replaceText("test"), closeSoftKeyboard());

        onView(withId(R.id.addBtn))
                .perform(click());

        //TODO get the broadcast window

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
