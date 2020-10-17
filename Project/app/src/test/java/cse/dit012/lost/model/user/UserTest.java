package cse.dit012.lost.model.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private final String userId = "userid";
    private final String name = "1234";
    private final String surname = "abcd";
    private User user;

    @Before
    public void setUp() {
        user = new User(userId, name, surname);
    }

    @Test
    public void getId() {
        assertEquals(userId, user.getId());
    }

    @Test
    public void getName() {
        assertEquals(name, user.getName());
    }

    @Test
    public void getSurname() {
        assertEquals(surname, user.getSurname());
    }

    @Test
    public void changeName() {
        String newName = "12345";
        user.changeName(newName);
        assertEquals(newName, user.getName());
    }

    @Test
    public void changeSurname() {
        String newSurname = "abcdef";
        user.changeSurname(newSurname);
        assertEquals(newSurname, user.getSurname());
    }
}