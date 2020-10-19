package cse.dit012.lost.model.course;

import org.junit.Test;

import cse.dit012.lost.model.broadcast.BroadcastId;

import static org.junit.Assert.assertEquals;

public class CourseCodeTest {
    String course = "DIT000";
    CourseCode code = new CourseCode(course);

    @Test
    public void equalsTest() {
        assertEquals(new CourseCode(course), code);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(code.hashCode(), new CourseCode(course).hashCode());
    }

    @Test
    public void toStringTest(){
        assertEquals(code.toString(), course);
    }
}
