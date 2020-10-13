package cse.dit012.lost.android.ui.map;

import android.os.Bundle;

import androidx.test.espresso.ViewInteraction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import cse.dit012.lost.R;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.service.BroadcastService;
import cse.dit012.lost.service.MailAndPasswordLoginService;
import cse.dit012.lost.service.UserInfoService;

import static androidx.fragment.app.testing.FragmentScenario.launchInContainer;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.OWNER_ID;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.PARAM_COURSE;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.PARAM_DESCRIPTION;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.PARAM_ID;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;


public class BroadcastInfoWindowFragmentTest {

    MapCoordinates coordinates = new MapCoordinates(0, 0);
    String description = "test";
    CourseCode code = new CourseCode("DIT000");
    Broadcast broadcast;

    @Before //Adds a test-broadcast
    public void setup() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = new CompletableFuture();
        MailAndPasswordLoginService login = new MailAndPasswordLoginService();
        login.userSignIn("sophia_pham@hotmail.com", "abc1234", success -> {
            completableFuture.complete(null);
        });
        completableFuture.get();
        UserInfoService uis = new UserInfoService();
        BroadcastService broadcastService = BroadcastService.get();
        broadcast = broadcastService.createBroadcast(uis.getID(), coordinates, code, description).get();
        Bundle args = new Bundle();
        args.putString(PARAM_COURSE, broadcast.getCourse().toString());
        args.putString(PARAM_DESCRIPTION, broadcast.getDescription());
        args.putString(PARAM_ID, broadcast.getId().toString());
        args.putString(OWNER_ID, broadcast.getOwnerUID());
        launchInContainer(BroadcastInfoWindowFragment.class, args);
    }

    @After
    public void refresh() {
        //onView(withId(R.id.delete)).perform(click());
    }

    //The course displayed should be the same as the one in the database
    @Test
    public void onViewCreatedCourse() {
        onView(withId(R.id.course))
                .check(matches(withText(broadcast.getCourse().toString())));
        //Checks if the position of the course is above description
        onView(withId(R.id.course))
                .check(isCompletelyAbove(withId(R.id.description)));
    }

    //The description displayed should be the same as the one in the database
    @Test
    public void onViewCreatedDescription() {
        onView(withId(R.id.description))
                .check(matches(withText(broadcast.getDescription())));
        //Checks if the position of the description is below course
        onView(withId(R.id.description))
                .check(isCompletelyBelow(withId(R.id.course)));
    }

    //The course should not change in the View after clicking the cancel button
    @Test
    public void cancelCourseInfoWindowView() {
        onView(withId(R.id.editInfoWindowButton)).perform(click());
        onView(withId(R.id.cancelInfoWindowButton)).perform(click());
        onView(withId(R.id.course)).check(matches(withText(code.toString())));
    }

    //The description should not change in the View after clicking the cancel button
    @Test
    public void cancelDescriptionInfoWindowView() {
        onView(withId(R.id.editInfoWindowButton)).perform(click());
        onView(withId(R.id.cancelInfoWindowButton)).perform(click());
        onView(withId(R.id.description)).check(matches(withText(description)));
    }

    //The course should change to the right course in the View after clicking the save button
    @Test
    public void saveCourseInfoWindowView() {
        ViewInteraction courseView = onView(withId(R.id.editCourseText));
        onView(withId(R.id.editInfoWindowButton)).perform(click());
        courseView.perform(replaceText("DIT123"));
        onView(withId(R.id.saveInfoWindowButton)).perform(click());
        courseView.check(matches(withText("DIT123")));
    }

    //The description should change to the right description in the View after clicking the save button
    @Test
    public void saveDescriptionInfoWindowView() {
        ViewInteraction descriptionView = onView(withId(R.id.editDescriptionText));
        onView(withId(R.id.editInfoWindowButton)).perform(click());
        descriptionView.perform(replaceText("test123"));
        onView(withId(R.id.saveInfoWindowButton)).perform(click());
        descriptionView.check(matches(withText("test123")));
    }

    //The course edited and then canceled should be not be changed in the database
    @Test
    public void cancelCourseInfoWindowDatabase() {
        onView(withId(R.id.editInfoWindowButton)).perform(click());
        onView(withId(R.id.editCourseText)).perform(replaceText("DIT123"));
        onView(withId(R.id.cancelInfoWindowButton)).perform(click());
        onView(withId(R.id.course)).check(matches(withText(broadcast.getCourse().toString())));
    }

    //The description edited and then canceled should be not be changed in the database
    @Test
    public void cancelDescriptionInfoWindowDatabase() {
        onView(withId(R.id.editInfoWindowButton)).perform(click());
        onView(withId(R.id.editDescriptionText)).perform(replaceText("test123"));
        onView(withId(R.id.cancelInfoWindowButton)).perform(click());
        onView(withId(R.id.description)).check(matches(withText(broadcast.getDescription())));
    }

    //The course edited should be updated in the database
    @Test
    public void editCourseInfoWindowDatabase() {
        onView(withId(R.id.editInfoWindowButton)).perform(click());
        onView(withId(R.id.editCourseText)).perform(replaceText("DIT123"));
        onView(withId(R.id.saveInfoWindowButton)).perform(click());
        onView(withId(R.id.course)).check(matches(withText("DIT123")));
    }

    //The description edited should be updated in the database
    @Test
    public void editDescriptionInfoWindowDatabase() {
        onView(withId(R.id.editInfoWindowButton)).perform(click());
        onView(withId(R.id.editDescriptionText)).perform(replaceText("test123"));
        onView(withId(R.id.saveInfoWindowButton)).perform(click());
        onView(withId(R.id.description)).check(matches(withText("test123")));
    }

    //The delete button should delete the broadcast
    @Test
    public void deleteBroadcast() {
        //onView(withId(R.id.delete)).perform(click());
        //onView(withId(R.id.frameLayout)).check(doesNotExist());
    }
}