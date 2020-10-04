package cse.dit012.lost.model.broadcast;

import org.junit.Test;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.course.CourseCode;

import static org.junit.Assert.*;

public class BroadcastTest {
    String id = "1";
    BroadcastId bId = new BroadcastId(id);
    Date date = new Date();
    Date activeDate = new Date(1,1,1);
    MapCoordinates coordinates= new MapCoordinates(2,2);
    String description = "test";
    CourseCode code = new CourseCode("dit111");
    Broadcast test = new Broadcast(bId,date,activeDate,coordinates,code,description);
    @Test
    public void getId() {
        assertEquals(test.getId(),id);
    }

    @Test
    public void getCreatedAt() {
        assertEquals(test.getCreatedAt(),date);
    }

    @Test
    public void getLastActive() {
        assertEquals(test.getLastActive(),activeDate);
    }

    @Test
    public void updateLastActive() {
        test.updateLastActive();
        assertNotEquals(test.getLastActive(),activeDate);

    }

    @Test
    public void getCoordinates() {
        assertEquals(coordinates,test.getCoordinates());
    }

    @Test
    public void getCourse() {
        assertEquals(test.getCourse(),code);
    }

    @Test
    public void updateCourse() {
        CourseCode newCode = new CourseCode("tda222");
        test.updateCourse(newCode);
        assertEquals(newCode,test.getCourse());
    }

    @Test
    public void getDescription() {
        assertEquals(description,test.getDescription());
    }

    @Test
    public void updateDescription(){
        String newDescription = "test2";
        test.updateDescription(newDescription);
        assertEquals(newDescription,test.getDescription());
    }


}