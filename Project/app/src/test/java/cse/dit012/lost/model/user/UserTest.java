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
        String surname = "abcd";
        User user = new User(name,surname);
        assertEquals(name,user.getName());
    }
    @Test
    public void getSurname() {
        String name = "1234";
        String surname = "abcd";
        User user = new User(name,surname);
        assertEquals(surname,user.getSurname());
    }

    @Test
    public void changeName() {
        String newName = "12345";
        String name = "123";
        String surname = "abcd";
        String newSurname = "abcdef";
        User user = new User(name,surname);
        user.changeName(newName);
        assertEquals(newName,user.getName());
    }

    @Test
    public void changeSurname() {
        String newName = "12345";
        String name = "123";
        String surname = "abcd";
        String newSurname = "abcdef";
        User user = new User(name,surname);
        user.changeSurname(newName);
        assertEquals(newSurname,user.getSurname());
    }

}