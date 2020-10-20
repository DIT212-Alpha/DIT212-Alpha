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

    @Test
    public void getCourseCode() {
        assertEquals(m.getCourseCode().getValue(),"");
        assertNotEquals(m.getCourseCode().getValue(),"test");
    }

    @Test
    @UiThreadTest
    public void setCourseCode() {
        String code = "test";
        m.setCourseCode(code);
        assertNotEquals(m.getCourseCode().getValue(),"");
        assertEquals(m.getCourseCode().getValue(),code);
    }
    @Test(expected = NullPointerException.class)
    @UiThreadTest
    public void setCourseCodeNull() {
        m.setCourseCode(null);
    }


    @Test
    public void getActiveBroadcastsFilteredByCourse() throws ExecutionException, InterruptedException {
        BroadcastService bs = BroadcastService.INSTANCE;
        Date createdAt = new Date(System.currentTimeMillis());
        Date lastActive = new Date(System.currentTimeMillis());
        MapCoordinates coordinates = new MapCoordinates(1,1);
        UserId userId = new UserId("test");
        CourseCode course = new CourseCode("course");
        CourseCode anotherCourse = new CourseCode("anotherCourse");
        Broadcast b1 = bs.createBroadcast(userId,coordinates,course,"test1").get();
        Broadcast b2 = bs.createBroadcast(userId,coordinates,course,"test1").get();
        Broadcast b3 = bs.createBroadcast(userId,coordinates,anotherCourse,"test2").get();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            m.setCourseCode(course.toString());
            LiveData<List<Broadcast>> livebl = m.getActiveBroadcastsFilteredByCourse();
            livebl.observeForever(new Observer<List<Broadcast>>() {
                @Override
                public void onChanged(List<Broadcast> broadcasts) {
                    livebl.removeObserver(this);
                }
            });
            List<Broadcast> bl = livebl.getValue();
            assertEquals(2, bl.size());
            assertEquals(bl.get(0).getDescription(),"test1");
        });
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            m.setCourseCode(course.toString());
            LiveData<List<Broadcast>> livebl = m.getActiveBroadcastsFilteredByCourse();
            livebl.observeForever(new Observer<List<Broadcast>>() {
                @Override
                public void onChanged(List<Broadcast> broadcasts) {
                    livebl.removeObserver(this);
                }
            });
            List<Broadcast> bl = livebl.getValue();
            assertEquals(bl.size(),1);
            assertEquals(bl.get(0).getDescription(),"test2");
        });



    }
}