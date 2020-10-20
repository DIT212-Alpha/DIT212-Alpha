package cse.dit012.lost.service.broadcast;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import cse.dit012.lost.BroadcastRepositoryProvider;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;
import java9.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Author: Mathias Drage
 */
public class BroadcastServiceTest {
    BroadcastRepository br = BroadcastRepositoryProvider.get();
    BroadcastService bs = BroadcastServiceFactory.get();

    /**
     * Tests that a broadcast is successfully created
     */
    @Test
    public void createBroadcast() throws ExecutionException, InterruptedException {
        CompletableFuture<Broadcast> result = bs.createBroadcast(new UserId("uid"),
                new MapCoordinates(1, 1),
                new CourseCode("testCourse"),
                "description");
        Broadcast b = result.get();
        assertEquals(b, br.getById(b.getId()).get());
    }

    /**
     * Tests if a created broadcasts LastActive date is updated after
     * updateBroadcastLastActive() is run
     */
    @Test
    public void updateBroadcastLastActive() throws ExecutionException, InterruptedException {
        CompletableFuture<Broadcast> result = bs.createBroadcast(new UserId("uid"),
                new MapCoordinates(1, 1),
                new CourseCode("testCourse"),
                "description");
        Date date = result.get().getLastActive();
        result = bs.updateBroadcastLastActive(result.get().getId());
        assertNotEquals(date, result.get().getLastActive());
    }

    /**
     * Tests that a broadcast i set to inactive (lastactive date is more than one minute behind
     * current date, the broadcast classifies as inactive)
     */
    @Test
    public void setBroadcastInactive() throws ExecutionException, InterruptedException {
        CompletableFuture<Broadcast> result = bs.createBroadcast(new UserId("uid"),
                new MapCoordinates(1, 1),
                new CourseCode("testCourse"),
                "description");
        Date date = result.get().getLastActive();
        result.get().setToInactive();
        assertTrue(date.after(result.get().getLastActive()));
    }

    /**
     * Tests than a broadcast can be edited, and that the broadcastobject remains the same
     */
    @Test
    public void editBroadcast() throws ExecutionException, InterruptedException {
        CompletableFuture<Broadcast> result = bs.createBroadcast(new UserId("uid"),
                new MapCoordinates(1, 1),
                new CourseCode("testCourse"),
                "description");
        Broadcast b = result.get();
        result = bs.editBroadcast(b.getId(), new CourseCode("another course"), "new description");
        assertEquals(b, result.get());
        assertTrue(result.get().getId().equals(b.getId()));
        assertEquals(result.get().getDescription(), "new description");
    }
}