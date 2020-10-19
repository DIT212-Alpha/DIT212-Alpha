package cse.dit012.lost.android.ui;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cse.dit012.lost.R;
import cse.dit012.lost.service.authenticateduser.AuthenticatedUserService;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * @Author: Sophia Pham
 * */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginRegisterProcessTestRecorder {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    /**
     * @Author: Sophia Pham
     * This is a test process of logging in and registering an account.
     * */

    @Test
    public void loginRegisterProcessTestRecorder() throws InterruptedException {
        AuthenticatedUserService.userService.signOutUser();

        onView(withId(R.id.editTextEmail))
                .perform(replaceText("test@test.com"), closeSoftKeyboard());

        onView(withId(R.id.editTextPassword))
                .perform(replaceText("test123"), closeSoftKeyboard());

        onView(withId(R.id.editTextPassword))
                .perform(pressImeActionButton());

        onView(withId(R.id.cirLoginButton))
                .perform(click());

        Thread.sleep(1000);

        onView(withId(R.id.sign_out_btn))
                .perform(click());

        onView(withId(R.id.editTextEmail))
                .perform(replaceText("thisshouldfail"), closeSoftKeyboard());

        onView(withId(R.id.cirLoginButton))
                .perform(click());

        onView(withId(R.id.editTextPassword))
                .perform(replaceText("123"), closeSoftKeyboard());

        onView(withId(R.id.cirLoginButton))
                .perform(click());

        onView(withId(R.id.editTextPassword))
                .perform(click());

        onView(withId(R.id.editTextPassword))
                .perform(replaceText("1234567"));

        onView(withId(R.id.editTextPassword))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.cirLoginButton))
                .perform(click());

        onView(withId(R.id.clickable_text_new_user))
                .perform(click());

        onView(withId(R.id.registerTextUserName))
                .perform(replaceText("Lorenzo"), closeSoftKeyboard());

        onView(withId(R.id.registerTextSurName))
                .perform(replaceText("Von Matterhorn"), closeSoftKeyboard());

        onView(withId(R.id.cirRegisterButton))
                .perform(click());

        onView(withId(R.id.registerTextEmail))
                .perform(replaceText("lorenzovonmatterhorn"), closeSoftKeyboard());

        onView(withId(R.id.registerTextEmail))
                .perform(click());

        onView(withId(R.id.registerTextEmail))
                .perform(replaceText("lorenzovon@matterhorn.com"));

        onView(withId(R.id.registerTextEmail))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.registerTextPassword))
                .perform(replaceText("lorenzo123"), closeSoftKeyboard());

        onView(withId(R.id.registerTextPassword))
                .perform(pressImeActionButton());

        onView(withId(R.id.cirRegisterButton))
                .perform(click());

        Thread.sleep(1000);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        onView(withId(R.id.sign_out_btn))
                .perform(click());

        user.delete();

    }
}
