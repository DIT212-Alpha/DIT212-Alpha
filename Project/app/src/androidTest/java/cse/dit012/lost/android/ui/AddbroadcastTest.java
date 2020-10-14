package cse.dit012.lost.android.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cse.dit012.lost.R;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddbroadcastTest {

    /** this test aims to check if an exiosting user can add a broadcast
     * requires the user to NOT be logged in.
     */

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void addbroadcastTest() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.editTextEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                1),
                        isDisplayed()));
        editText.perform(replaceText("pontus.nellgard@gmail.com"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.editTextPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                8),
                        isDisplayed()));
        editText2.perform(replaceText("password"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.editTextPassword), withText("password"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                8),
                        isDisplayed()));
        editText3.perform(pressImeActionButton());

        ViewInteraction button = onView(
                allOf(withId(R.id.cirLoginButton), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                0),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.broadcast_btn), withText("Broadcast"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                1),
                        isDisplayed()));
        button2.perform(click());

        ViewInteraction spinner = onView(
                allOf(withId(R.id.courseSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                0),
                        isDisplayed()));
        spinner.perform(click());

        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        checkedTextView.perform(click());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.descriptionEdittext),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                3),
                        isDisplayed()));
        editText4.perform(replaceText("test Description"), closeSoftKeyboard());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.addBtn), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                1),
                        isDisplayed()));
        button3.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
