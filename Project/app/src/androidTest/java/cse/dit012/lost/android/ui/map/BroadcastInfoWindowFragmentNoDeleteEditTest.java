package cse.dit012.lost.android.ui.map;

import android.os.Bundle;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cse.dit012.lost.R;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;

import cse.dit012.lost.service.broadcast.BroadcastService;
import cse.dit012.lost.service.login.LoginService;
import cse.dit012.lost.service.login.LoginServiceFactory;

import static androidx.fragment.app.testing.FragmentScenario.launchInContainer;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.OWNER_ID;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.PARAM_COURSE;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.PARAM_DESCRIPTION;
import static cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment.PARAM_ID;
import static org.hamcrest.Matchers.not;

public class BroadcastInfoWindowFragmentNoDeleteEditTest {
    private final MapCoordinates coordinates = new MapCoordinates(0, 0);
    private final String description = "test";
    private final CourseCode code = new CourseCode("DIT000");
    private Broadcast broadcast;

    //As a non-owner of the broadcast you should not be able to delete or edit
    @Test //(expected = IllegalArgumentException.class)
    public void noDeleteEdit() throws InterruptedException, ExecutionException, TimeoutException {
        // Login
        LoginService login = LoginServiceFactory.createEmailAndPasswordService("test@test.com", "test123");
        login.login().get(5, TimeUnit.SECONDS);

        // Create dummy test broadcast, with a different owner.
        BroadcastService broadcastService = BroadcastService.INSTANCE;
        broadcast = broadcastService.createBroadcast(new UserId("abc"), coordinates, code, description).get();

        // Launch fragment
        Bundle args = new Bundle();
        args.putString(PARAM_COURSE, broadcast.getCourse().toString());
        args.putString(PARAM_DESCRIPTION, broadcast.getDescription());
        args.putString(PARAM_ID, broadcast.getId().toString());
        args.putString(OWNER_ID, broadcast.getOwnerUID().toString());
        launchInContainer(BroadcastInfoWindowFragment.class, args);

        onView(withId(R.id.delete)).check(matches(not(isDisplayed())));
    }


}
