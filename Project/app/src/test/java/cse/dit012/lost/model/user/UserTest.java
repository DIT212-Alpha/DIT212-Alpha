package cse.dit012.lost.model.user;

import org.junit.Test;

import cse.dit012.lost.model.BroadcastObject;
import cse.dit012.lost.model.BroadcastObjectFactory;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.course.Course;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void getName() {
        String name = "1234";
        User user = new User("1234");
        assertEquals(name,user.getName());
    }

    @Test
    public void changeName() {
        String newName = "12345";
        String name = "123";
        User user = new User(name);
        user.changeName(newName);
        assertEquals(newName,user.getName());
    }

    @Test
    public void addBroadcastObject() {
        BroadcastObject course1 = BroadcastObjectFactory.createBroadcastObject("test1");
        BroadcastObject course2 = BroadcastObjectFactory.createBroadcastObject("test2");
        BroadcastObject course3 = BroadcastObjectFactory.createBroadcastObject("test3");
        User user = new User("user");
        assertNull(user.getObject(2));
        user.addBroadcastObject(course1);
        user.addBroadcastObject(course2);
        user.addBroadcastObject(course3);
        assertEquals(user.getObject(0),course1);
        assertEquals(user.getObject(1),course2);
        assertEquals(user.getObject(2),course3);
    }

    @Test
    public void deleteBroadcastObject() {
        BroadcastObject course1 = BroadcastObjectFactory.createBroadcastObject("test1");
        BroadcastObject course2 = BroadcastObjectFactory.createBroadcastObject("test2");
        BroadcastObject course3 = BroadcastObjectFactory.createBroadcastObject("test3");
        User user = new User("user");
        user.addBroadcastObject(course1);
        user.addBroadcastObject(course2);
        user.addBroadcastObject(course3);
        assertEquals(user.getObject(1),course2);
        user.deleteBroadcastObject(1);
        assertEquals(user.getObject(1),course3);
    }

    @Test
    public void getObject() {
        User user = new User("user");
        BroadcastObject course1 = BroadcastObjectFactory.createBroadcastObject("test1");
        assertNull(user.getObject(5));
        user.addBroadcastObject(course1);
        assertEquals(user.getObject(0),course1);

    }
}