package cse.dit012.lost.model.broadcast;

import org.junit.Test;

import cse.dit012.lost.model.user.UserId;

import static org.junit.Assert.assertEquals;

/**
 * @Author: Sophia Pham
 */

public class BroadcastIdTest {
    String id = "0";
    BroadcastId bId = new BroadcastId(id);

    @Test
    public void equalsTest() {
        assertEquals(new BroadcastId(id), bId);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(bId.hashCode(), new BroadcastId(id).hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals(bId.toString(), id);
    }

    /**
     * Test if exceptions is thrown if string is empty or null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testException() {
        new BroadcastId("");
    }

    @Test(expected = NullPointerException.class)
    public void testException2() {
        new BroadcastId(null);
    }
}
