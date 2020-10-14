package cse.dit012.lost.android.ui;


import android.os.Bundle;


import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cse.dit012.lost.R;
import cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment;
import cse.dit012.lost.android.ui.screen.map.AddBroadcastFragment;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.service.BroadcastService;
import cse.dit012.lost.service.FirebaseUserInfoService;
import cse.dit012.lost.service.MailAndPasswordLoginService;

import static androidx.fragment.app.testing.FragmentScenario.launchInContainer;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class addbroadcastTest {

    /**
     * This test is used to add a broadcast. The pre-requisites are that the user is logged in and the locationn settings are allowed
     * Please don't run this test while adding other broadcasts since the observers only listens on newly added broadcasts
     * Make sure you delete the broadcast between tests !!! (As there is no way of getting the broadcast id from a test environment
     * OBS! there hjas to exist at least one broadcast for the emptybroadcast test to work. otherwise it times out
     */

    @Before //Adds a test-broadcast
    public void setup() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = new CompletableFuture();
        MailAndPasswordLoginService login = new MailAndPasswordLoginService();
        login.userSignIn("pontus.nellgard@gmail.com", "password", success -> {
            completableFuture.complete(null);
        });
        completableFuture.get();
        Bundle args = new Bundle();

       // launchInContainer(AddBroadcastFragment.class, args);
        //    final NavController navController = Navigation.findNavController(mActivityTestRule.getActivity().findViewById(R.id.broadcast_btn));
      //  navController.navigate(R.id.action_loginFragment_to_mapScreenFragment);

        // Create a TestNavHostController
        TestNavHostController navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());
        //navController.setGraph(R.navigation.nav_graph);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            // navController.navigate(R.id.action_mapScreenFragment_to_add_broadcast_fragment);

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

        onView(withId(R.id.descriptionEdittext))
                .perform(replaceText("test description"), closeSoftKeyboard());

        onView(withId(R.id.addBtn))
                .perform(click());

        BroadcastRepository repository = BroadcastRepository.get();

        java9.util.concurrent.CompletableFuture<List<Broadcast>> future = new java9.util.concurrent.CompletableFuture<>();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            LiveData<List<Broadcast>> observableBroadcasts = repository.observeActiveBroadcasts();
            observableBroadcasts.observeForever(new Observer<List<Broadcast>>() {
                @Override
                public void onChanged(List<Broadcast> broadcast) {
                    if (!broadcast.isEmpty()) { // Keep listening and waiting if no broadcasts came back yet
                        observableBroadcasts.removeObserver(this);
                        future.complete(broadcast);
                    }
                }
            });
        });

        List<Broadcast> returnedBroadcasts = future.get(5, TimeUnit.SECONDS);
        boolean exists = false;
        for (int i = 0; i<returnedBroadcasts.size();i++){
            if(returnedBroadcasts.get(i).getDescription().equals("test description")){
                exists = true;
               break;
            }
        }
        assertTrue("Broadcast not found", exists);
    }

    @Test // Tests if a user tries to add a broadcast without setting a description which should not be possible
    public void addEmptyBroadcast() throws InterruptedException, ExecutionException, TimeoutException {

        onView(withId(R.id.courseSpinner)).perform(click());

        //Selects the second item in the course select spinner
        onData(anything()).atPosition(1).perform(click());

       /* onView(withId(R.id.descriptionEdittext))
                .perform(replaceText("test description"), closeSoftKeyboard());*/

        onView(withId(R.id.addBtn))
                .perform(click());

        BroadcastRepository repository = BroadcastRepository.get();

        java9.util.concurrent.CompletableFuture<List<Broadcast>> future = new java9.util.concurrent.CompletableFuture<>();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            LiveData<List<Broadcast>> observableBroadcasts = repository.observeActiveBroadcasts();
            observableBroadcasts.observeForever(new Observer<List<Broadcast>>() {
                @Override
                public void onChanged(List<Broadcast> broadcast) {
                    if (!broadcast.isEmpty()) { // Keep listening and waiting if no broadcasts came back yet
                        observableBroadcasts.removeObserver(this);
                        future.complete(broadcast);
                    }
                }
            });
        });

        List<Broadcast> returnedBroadcasts = future.get(5, TimeUnit.SECONDS);
        boolean exists = false;
        for (int i = 0; i<returnedBroadcasts.size();i++){
            if(returnedBroadcasts.get(i).getDescription().equals("test description")){
                exists = true;
                break;
            }
        }
        assertFalse("Broadcast was found when it should not", exists);
    }
}
