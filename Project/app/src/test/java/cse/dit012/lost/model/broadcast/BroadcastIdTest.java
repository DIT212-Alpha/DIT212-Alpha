package cse.dit012.lost.model.broadcast;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author: Sophia Pham
 * */

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
}
