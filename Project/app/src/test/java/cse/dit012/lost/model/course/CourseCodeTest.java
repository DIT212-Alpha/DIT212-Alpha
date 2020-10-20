package cse.dit012.lost.model.course;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author: Sophia Pham
 */

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
    public void toStringTest() {
        assertEquals(code.toString(), course);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyString() {
        new CourseCode("");
    }

    @Test(expected = NullPointerException.class)
    public void nullString() {
        new CourseCode(null);
    }

}
