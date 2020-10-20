package cse.dit012.lost.android.ui.screen.map;

import android.app.Activity;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import cse.dit012.lost.BroadcastRepositoryProvider;
import cse.dit012.lost.R;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.service.login.LoginService;
import cse.dit012.lost.service.login.LoginServiceFactory;
import java9.util.concurrent.CompletableFuture;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddBroadcastFragmentTest {

    /**
     * This test is used to add a broadcast. The pre-requisites are that the user is logged in and the locationn settings are allowed
     * Please don't run this test while adding other broadcasts since the observers only listens on newly added broadcasts
     * Make sure you delete the broadcast between tests !!! (As there is no way of getting the broadcast id from a test environment
     * OBS! there hjas to exist at least one broadcast for the emptybroadcast test to work. otherwise it times out
     */

    private TestNavHostController navController;

    @Before
    public void setup() throws ExecutionException, InterruptedException, TimeoutException {
        LoginService login = LoginServiceFactory.createEmailAndPasswordService("pontus.nellgard@gmail.com", "password");
        login.login().get(5, TimeUnit.SECONDS);

        // Create a TestNavHostController
        navController = new TestNavHostController(ApplicationProvider.getApplicationContext());
        getInstrumentation().runOnMainSync(() -> {
            navController.setGraph(R.navigation.nav_graph);
            navController.setCurrentDestination(R.id.add_broadcast_fragment);
        });

        // Create a graphical FragmentScenario for the TitleScreen
        FragmentScenario<AddBroadcastFragment> titleScenario = FragmentScenario.launchInContainer(AddBroadcastFragment.class);

        // Set the NavController property on the fragment
        titleScenario.onFragment(fragment ->
                Navigation.setViewNavController(fragment.requireView(), navController)
        );
    }

    //Allows the application to use the location
    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");


    //In order to add a broadcast the user has to be logged in
    @Test
    public void addBroadcast() throws InterruptedException, ExecutionException, TimeoutException {
        onView(withId(R.id.courseSpinner)).perform(click());

        //Selects the second item in the course select spinner
        onData(anything()).atPosition(1).perform(click());

        String randomDescription = "" + new Random().nextDouble();
        onView(withId(R.id.descriptionEdittext))
                .perform(replaceText(randomDescription), closeSoftKeyboard());

        onView(withId(R.id.addBtn))
                .perform(click());

        BroadcastRepository repository = BroadcastRepositoryProvider.get();

        CompletableFuture<List<Broadcast>> future = new CompletableFuture<>();
        getInstrumentation().runOnMainSync(() -> {
            LiveData<List<Broadcast>> observableBroadcasts = repository.observeActiveBroadcasts();
            observableBroadcasts.observeForever(new Observer<List<Broadcast>>() {
                @Override
                public void onChanged(List<Broadcast> broadcast) {
                    observableBroadcasts.removeObserver(this);
                    future.complete(broadcast);
                }
            });
        });

        List<Broadcast> returnedBroadcasts = future.get(5, TimeUnit.SECONDS);
        boolean exists = false;
        for (int i = 0; i < returnedBroadcasts.size(); i++) {
            if (returnedBroadcasts.get(i).getDescription().equals(randomDescription)) {
                exists = true;
                break;
            }
        }
        assertTrue("Broadcast not found", exists);
        // Navigation goes back to map screen
        assertEquals(navController.getCurrentDestination().getId(), R.id.mapScreenFragment);
    }

    @Test
    // Tests if a user tries to add a broadcast without setting a description which should not be possible
    public void addEmptyBroadcast() {
        onView(withId(R.id.courseSpinner)).perform(click());

        //Selects the second item in the course select spinner
        onData(anything()).atPosition(1).perform(click());

        onView(withId(R.id.addBtn))
                .perform(click());

        // Toast shows up
        onView(withText("Please select a course and set a description"))
                .inRoot(withDecorView(not(getActivityInstance().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        // Navigation does not go back to map screen
        assertEquals(navController.getCurrentDestination().getId(), R.id.add_broadcast_fragment);
    }

    @Test
    // Tests if pressing the cancel button goes back to map screen
    public void cancelGoesToMap() {
        onView(withId(R.id.cancelBtn))
                .perform(click());

        // Navigation does not go back to map screen
        assertEquals(navController.getCurrentDestination().getId(), R.id.mapScreenFragment);
    }

    public Activity getActivityInstance() {
        AtomicReference<Activity> currentActivity = new AtomicReference<>();
        getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> resumedActivities =
                    ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
            if (resumedActivities.iterator().hasNext()) {
                currentActivity.set(resumedActivities.iterator().next());
            }
        });

        return currentActivity.get();
    }
}
