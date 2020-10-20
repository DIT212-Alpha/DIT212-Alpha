package cse.dit012.lost.android.ui.screen.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.test.annotation.UiThreadTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import cse.dit012.lost.android.ui.MainActivity;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;
import cse.dit012.lost.service.broadcast.BroadcastService;

import static org.junit.Assert.*;

public class MapViewModelTest {
    MapViewModel m = new MapViewModel();

    /**
     * Tests that getCourseCode retrieves empty string when no courseCode is set
     */
    @Test
    public void getCourseCode() {
        assertEquals(m.getCourseCode(),"");
        assertNotEquals(m.getCourseCode(),"test");
    }

    /**
     * Checks that a courseCode can be set, and that the right courseCode is retrieved
     * after calling get method
     */
    @Test
    @UiThreadTest
    public void setCourseCode() {
        String code = "test";
        m.setCourseCode(code);
        assertNotEquals(m.getCourseCode(),"");
        assertEquals(m.getCourseCode(),code);
    }
    @Test(expected = NullPointerException.class)
    @UiThreadTest
    public void setCourseCodeNull() {
        m.setCourseCode(null);
    }


    /**
     * Creates broadcasts and adds them to the Firebase, then runs the filter method and checks
     * expected number of courses is filtered away
     */
    @Test
    public void getActiveBroadcastsFilteredByCourse() throws ExecutionException, InterruptedException {
        //broadcast service to add broadcasts to firebase
        BroadcastService bs = BroadcastService.INSTANCE;
        //Objects used to create broadcasts
        MapCoordinates coordinates = new MapCoordinates(1,1);
        UserId userId = new UserId("test");
        UserId userId2 = new UserId("test2");
        CourseCode course = new CourseCode("filterTestCourse");
        CourseCode anotherCourse = new CourseCode("anotherFilterTestCourse");
        //add courses to database
        bs.createBroadcast(userId,coordinates,course,"test1").get();
        bs.createBroadcast(userId2,coordinates,course,"test1").get();
        bs.createBroadcast(userId,coordinates,anotherCourse,"test2").get();
        //Objects that get value when a process is finished
        CompletableFuture<List<Broadcast>> future = new CompletableFuture<>();
        //Lambda expression for running instructions on Ui Thread, which is required for the test to run
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            //Set courseCode to filter on
            m.setCourseCode(course.toString());
            //Get filtered broadcasts from database
            LiveData<List<Broadcast>> livebl = m.getActiveBroadcastsFilteredByCourse();
            //Observer observing process of retrieving from Firebase
            livebl.observeForever(new Observer<List<Broadcast>>() {
                //What runs when observer is notified
                @Override
                public void onChanged(List<Broadcast> broadcasts) {
                    livebl.removeObserver(this);
                    future.complete(broadcasts);
                }
            });
        });
        //Assigns value
        List<Broadcast> bl = future.get();
        //Tests that expected number of broadcasts is retrieved after filtering
        assertEquals(2, bl.size());
        assertEquals(bl.get(0).getDescription(),"test1");

        //Same process as before, but with an another courseCode
        CompletableFuture<List<Broadcast>> future2 = new CompletableFuture<>();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            m.setCourseCode(anotherCourse.toString());
            LiveData<List<Broadcast>> livebl = m.getActiveBroadcastsFilteredByCourse();
            livebl.observeForever(new Observer<List<Broadcast>>() {
                @Override
                public void onChanged(List<Broadcast> broadcasts) {
                    livebl.removeObserver(this);
                    future2.complete(broadcasts);
                }
            });
        });
        bl = future2.get();
        assertEquals(1,bl.size());
        assertEquals(bl.get(0).getDescription(),"test2");
    }
}