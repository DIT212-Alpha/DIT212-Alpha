package cse.dit012.lost.model.user;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserIdTest {


    /**
     * Tests that two Userids with same id String is equal, and that they are not equal if they differ
     */
    @Test
    public void testEquals() {
        UserId test1= new UserId("123");
        UserId test2 = new UserId("abc");
        UserId test3 = new UserId("123");
        assertFalse(test1.equals(test2));
        assertTrue(test1.equals(test3));
    }

    /**
     * Tests that the hash code for two userids with same string is equal, and that it is not null
     */
    @Test
    public void testHashCode() {
        UserId test1= new UserId("123");
        UserId test2 = new UserId("123");
        UserId test3 = new UserId("abc");
        assertNotNull(test1.hashCode());
        assertEquals(test1.hashCode(),test2.hashCode());
        assertNotEquals(test1,test3);
    }

    /**
     * Tests that toString produces correct string
     */
    @Test
    public void testToString() {
        UserId test1= new UserId("123");
        UserId test2= new UserId("åæø!?%$#$");
        UserId test3= new UserId("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        assertEquals(test1.toString(),"123");
        assertEquals(test2.toString(),"åæø!?%$#$");
        assertEquals(test3.toString(),"111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        assertNotEquals(test1,"321");
    }

    /**
     * Test if exceptions is thrown if string is empty
     */
    @Test (expected = IllegalArgumentException.class)
    public void testException(){
        new UserId("");
    }

    @Test (expected = NullPointerException.class)
    public void testException2(){
        new UserId(null);
    }
}