package cse.dit012.lost.model.broadcast;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.course.CourseCode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BroadcastTest {
    private final String id = "1";
    private final String userId = "mrbob";
    private final BroadcastId bId = new BroadcastId(id);
    private final Date date = new Date();
    private final Date activeDate = new Date(System.currentTimeMillis() - 1000);
    private final MapCoordinates coordinates = new MapCoordinates(2, 2);
    private final String description = "test";
    private final CourseCode code = new CourseCode("dit111");
    private Broadcast test;

    @Before
    public void setUp() {
        test = new Broadcast(bId, userId, date, activeDate, coordinates, code, description);
    }

    @Test
    public void getId() {
        assertEquals(test.getId(), bId);
    }

    @Test
    public void getOwnerUID() {
        assertEquals(test.getOwnerUID(), "mrbob");
    }

    @Test
    public void getCreatedAt() {
        assertEquals(test.getCreatedAt(), date);
    }

    @Test
    public void getLastActive() {
        assertEquals(test.getLastActive(), activeDate);
    }

    @Test
    public void updateLastActive() {
        test.updateLastActive();
        assertNotEquals(test.getLastActive(), activeDate);
    }

    @Test
    public void setToInactive() {
        test.setToInactive();
        assertTrue(test.getLastActive().compareTo(new Date(System.currentTimeMillis())) < 0);
    }

    @Test
    public void getCoordinates() {
        assertEquals(coordinates, test.getCoordinates());
    }

    @Test
    public void getCourse() {
        assertEquals(test.getCourse(), code);
    }

    @Test
    public void updateCourse() {
        CourseCode newCode = new CourseCode("tda222");
        test.updateCourse(newCode);
        assertEquals(newCode, test.getCourse());
    }

    @Test
    public void getDescription() {
        assertEquals(description, test.getDescription());
    }

    @Test
    public void updateDescription() {
        String newDescription = "test2";
        test.updateDescription(newDescription);
        assertEquals(newDescription, test.getDescription());
    }
}