package cse.dit012.lost.android.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BroadcastInfoWindowFragmentTestRecorder {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void broadcastInfoWindowFragmentTestRecorder() {
        //TODO check automatic log in
        ViewInteraction editText = onView(
                allOf(withId(R.id.editTextEmail),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                1),
                        isDisplayed()));
        editText.perform(replaceText("sophia_pham@hotmail.com"), closeSoftKeyboard());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.editTextEmail), withText("sophia_pham@hotmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                1),
                        isDisplayed()));
        editText4.perform(closeSoftKeyboard());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.editTextEmail), withText("sophia_pham@hotmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                1),
                        isDisplayed()));
        editText5.perform(click());

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.editTextPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                8),
                        isDisplayed()));
        editText6.perform(replaceText("abc123"), closeSoftKeyboard());

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.editTextPassword), withText("abc123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                8),
                        isDisplayed()));
        editText7.perform(pressImeActionButton());

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

        ViewInteraction editText8 = onView(
                allOf(withId(R.id.descriptionEdittext),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                3),
                        isDisplayed()));
        editText8.perform(replaceText("test\n"), closeSoftKeyboard());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.addBtn), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment2),
                                        0),
                                1),
                        isDisplayed()));
        button3.perform(click());


        //TODO start here
        ViewInteraction button4 = onView(
                allOf(withId(R.id.editInfoWindowButton), withText("edit"),
                        childAtPosition(
                                allOf(withId(R.id.textViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                0)),
                                2),
                        isDisplayed()));
        button4.perform(click());

        ViewInteraction editText9 = onView(
                allOf(withId(R.id.editCourseText), withText("DIT012"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                1),
                        isDisplayed()));
        editText9.perform(replaceText("DIT123"));

        ViewInteraction editText10 = onView(
                allOf(withId(R.id.editCourseText), withText("DIT123"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                1),
                        isDisplayed()));
        editText10.perform(closeSoftKeyboard());

        ViewInteraction button5 = onView(
                allOf(withId(R.id.cancelInfoWindowButton), withText("CANCEL"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                3),
                        isDisplayed()));
        button5.perform(click());

        ViewInteraction button6 = onView(
                allOf(withId(R.id.editInfoWindowButton), withText("edit"),
                        childAtPosition(
                                allOf(withId(R.id.textViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                0)),
                                2),
                        isDisplayed()));
        button6.perform(click());

        ViewInteraction editText11 = onView(
                allOf(withId(R.id.editCourseText), withText("DIT012"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                1),
                        isDisplayed()));
        editText11.perform(replaceText("DIT123"));

        ViewInteraction editText12 = onView(
                allOf(withId(R.id.editCourseText), withText("DIT123"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                1),
                        isDisplayed()));
        editText12.perform(closeSoftKeyboard());

        ViewInteraction button7 = onView(
                allOf(withId(R.id.saveInfoWindowButton), withText("SAVE"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                2),
                        isDisplayed()));
        button7.perform(click());

        ViewInteraction button8 = onView(
                allOf(withId(R.id.editInfoWindowButton), withText("edit"),
                        childAtPosition(
                                allOf(withId(R.id.textViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                0)),
                                2),
                        isDisplayed()));
        button8.perform(click());

        ViewInteraction editText13 = onView(
                allOf(withId(R.id.editDescriptionText), withText("test"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                0),
                        isDisplayed()));
        editText13.perform(replaceText("test123"));

        ViewInteraction editText14 = onView(
                allOf(withId(R.id.editDescriptionText), withText("test123"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                0),
                        isDisplayed()));
        editText14.perform(closeSoftKeyboard());

        ViewInteraction button9 = onView(
                allOf(withId(R.id.cancelInfoWindowButton), withText("CANCEL"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                3),
                        isDisplayed()));
        button9.perform(click());

        ViewInteraction button10 = onView(
                allOf(withId(R.id.editInfoWindowButton), withText("edit"),
                        childAtPosition(
                                allOf(withId(R.id.textViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                0)),
                                2),
                        isDisplayed()));
        button10.perform(click());

        ViewInteraction editText15 = onView(
                allOf(withId(R.id.editDescriptionText), withText("test"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                0),
                        isDisplayed()));
        editText15.perform(replaceText("test123"));

        ViewInteraction editText16 = onView(
                allOf(withId(R.id.editDescriptionText), withText("test123"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                0),
                        isDisplayed()));
        editText16.perform(closeSoftKeyboard());

        ViewInteraction button11 = onView(
                allOf(withId(R.id.saveInfoWindowButton), withText("SAVE"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                2),
                        isDisplayed()));
        button11.perform(click());

        ViewInteraction button12 = onView(
                allOf(withId(R.id.editInfoWindowButton), withText("edit"),
                        childAtPosition(
                                allOf(withId(R.id.textViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                0)),
                                2),
                        isDisplayed()));
        button12.perform(click());

        ViewInteraction editText17 = onView(
                allOf(withId(R.id.editDescriptionText), withText("test"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                0),
                        isDisplayed()));
        editText17.perform(replaceText("test123"));

        ViewInteraction editText18 = onView(
                allOf(withId(R.id.editDescriptionText), withText("test123"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                0),
                        isDisplayed()));
        editText18.perform(closeSoftKeyboard());

        ViewInteraction button13 = onView(
                allOf(withId(R.id.saveInfoWindowButton), withText("SAVE"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                2),
                        isDisplayed()));
        button13.perform(click());

        ViewInteraction button14 = onView(
                allOf(withId(R.id.editInfoWindowButton), withText("edit"),
                        childAtPosition(
                                allOf(withId(R.id.textViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                0)),
                                2),
                        isDisplayed()));
        button14.perform(click());

        ViewInteraction button15 = onView(
                allOf(withId(R.id.cancelInfoWindowButton), withText("CANCEL"),
                        childAtPosition(
                                allOf(withId(R.id.editViewInfoBox),
                                        childAtPosition(
                                                withId(R.id.frameLayout),
                                                1)),
                                3),
                        isDisplayed()));
        button15.perform(click());
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
