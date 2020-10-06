package cse.dit012.lost;

import android.content.Context;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import cse.dit012.lost.android.ui.map.LostMapFragment;
import cse.dit012.lost.android.ui.screen.map.AddBroadcastFragment;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BroadcastTest {
    @Before
    public void setUp(){
        FragmentScenario.launchInContainer(AddBroadcastFragment.class);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("cse.dit012.lost", appContext.getPackageName());
    }

    /*@Test
    public void pressBroadcastBtn(){
        onView(withId(R.id.broadcast_btn)).perform(click());
    }*/


    @Test
    public void selectCourse(){
        onView(withId(R.id.courseSpinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
    }
    @Test
    public void addDescription(){
        onView(withId(R.id.descriptionEdittext)).perform(typeText("Adding test-description"));
    }

    @Test
    public void addButtonPress(){
        onView(withId(R.id.addBtn)).perform(click());
    }

    @Test
    public void fullAddBroadcastTest(){
        //Select course from spinner
        onView(withId(R.id.courseSpinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());

        //Add description
        onView(withId(R.id.descriptionEdittext)).perform(typeText("Adding full test-description"));

        //press add button
        onView(withId(R.id.addBtn)).perform(click());
    }


}
