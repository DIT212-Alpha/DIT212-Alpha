package cse.dit012.lost.model.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void getName() {
        String name = "1234";
        String surname = "abcd";
        User user = new User("userid", name, surname);
        assertEquals(name, user.getName());
    }

    @Test
    public void getSurname() {
        String name = "1234";
        String surname = "abcd";
        User user = new User("userid", name, surname);
        assertEquals("", surname, user.getSurname());
    }

    @Test
    public void changeName() {
        String name = "123";
        String surname = "abcd";
        String newName = "12345";
        User user = new User("userid", name, surname);
        user.changeName(newName);
        assertEquals(newName, user.getName());
    }

    @Test
    public void changeSurname() {
        String name = "123";
        String surname = "abcd";
        String newSurname = "abcdef";
        User user = new User("userid", name, surname);
        user.changeSurname(newSurname);
        assertEquals(newSurname, user.getSurname());
    }

}