package cse.dit012.lost.model.broadcast;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * @Author: Mathias Drage & Sophia Pham
 */

public class BroadcastTest {
    private final String id = "1";
    private final UserId userId = new UserId("mrbob");
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
        assertEquals(test.getOwnerUID(), new UserId("mrbob"));
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

    @Test
    public void isPointInRangeOfBroadcastTest() {
        assertFalse(test.isPointInRangeOfBroadcast(new MapCoordinates(0, 0)));
        assertTrue(test.isPointInRangeOfBroadcast(coordinates));
    }

    @Test
    public void equalsTest() {
        assertEquals(new Broadcast(bId, userId, date, activeDate, coordinates, code, description), test);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(test.hashCode(), new Broadcast(bId, userId, date, activeDate, coordinates, code, description).hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals(test.toString(), new Broadcast(bId, userId, date, activeDate, coordinates, code, description).toString());
    }

    @Test
    public void isActiveTest() {
        Broadcast b1 = new Broadcast(bId,userId,date,new Date(System.currentTimeMillis()),coordinates,code,description);
        assertTrue(b1.isActive(new Date(System.currentTimeMillis())));
        b1.setToInactive();
        assertFalse(b1.isActive(new Date(System.currentTimeMillis())));
    }

}