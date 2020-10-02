package cse.dit012.lost.model.course;

import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest {

    @Test
    public void getName() {
        String name = "dit123";
        Course test = new Course("dit123");
        assertEquals(test.getName(),name);

    }

    @Test
    public void changeName() {
        String name = "dit123";
        String newName = "dit321";
        Course test = new Course("dit123");
        test.changeName(newName);
        assertEquals(test.getName(),newName);
    }
}