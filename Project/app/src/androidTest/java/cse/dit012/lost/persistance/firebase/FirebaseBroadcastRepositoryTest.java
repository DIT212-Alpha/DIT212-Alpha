package cse.dit012.lost.persistance.firebase;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;
import cse.dit012.lost.service.BroadcastService;
import java9.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FirebaseBroadcastRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private BroadcastRepository repository;
    private BroadcastService broadcastService;

    @Before
    public void setup() {
        repository = new FirebaseBroadcastRepository(FirebaseDatabase.getInstance());
        broadcastService = BroadcastService.fromRepository(repository);
    }

    private Broadcast storeNewTestBroadcast() throws TimeoutException, ExecutionException, InterruptedException {
        MapCoordinates coordinates = new MapCoordinates(2, 2);
        UserId userId = new UserId("mrbob");
        String description = "test";
        CourseCode code = new CourseCode("dit111");
        return broadcastService.createBroadcast(userId, coordinates, code, description).get(5, TimeUnit.SECONDS);
    }

    @Test
    public void nextIdentity_returnsNotNull() {
        assertNotNull(repository.nextIdentity());
    }

    @Test
    public void nextIdentity_returnsNotEmpty() {
        assertNotEquals(repository.nextIdentity(), "");
    }

    @Test
    public void storeNewlyCreatedBroadcast_getByIdReturnsSameAsInserted() throws InterruptedException, ExecutionException, TimeoutException {
        Broadcast broadcast = storeNewTestBroadcast();

        Broadcast returnedBroadcast = repository.getById(broadcast.getId()).get(5, TimeUnit.SECONDS);

        assertEquals(returnedBroadcast.getId(), broadcast.getId());
        assertEquals(returnedBroadcast.getOwnerUID(), broadcast.getOwnerUID());
        assertEquals(returnedBroadcast.getLastActive(), broadcast.getLastActive());
        assertEquals(returnedBroadcast.getCreatedAt(), broadcast.getCreatedAt());
        assertEquals(returnedBroadcast.getCoordinates(), broadcast.getCoordinates());
        assertEquals(returnedBroadcast.getCourse(), broadcast.getCourse());
        assertEquals(returnedBroadcast.getDescription(), broadcast.getDescription());
    }

    @Test(expected = ExecutionException.class)
    public void getById_withNonExistentId_throwsException() throws InterruptedException, ExecutionException, TimeoutException {
        repository.getById(new BroadcastId("THIS ID SHOULD NOT EXIST")).get(5, TimeUnit.SECONDS);
    }

    @Test
    public void observeById_withNewlyCreatedBroadcast_observerIsCalledOnceWithSameAsInserted() throws InterruptedException, ExecutionException, TimeoutException {
        Broadcast broadcast = storeNewTestBroadcast();

        CompletableFuture<Broadcast> future = new CompletableFuture<>();
        LiveData<Broadcast> observableBroadcast = repository.observeById(broadcast.getId());
        observableBroadcast.observeForever(new Observer<Broadcast>() {
            @Override
            public void onChanged(Broadcast broadcast) {
                observableBroadcast.removeObserver(this);
                future.complete(broadcast);
            }
        });

        Broadcast returnedBroadcast = future.get(5, TimeUnit.SECONDS);

        assertEquals(returnedBroadcast.getId(), broadcast.getId());
        assertEquals(returnedBroadcast.getOwnerUID(), broadcast.getOwnerUID());
        assertEquals(returnedBroadcast.getLastActive(), broadcast.getLastActive());
        assertEquals(returnedBroadcast.getCreatedAt(), broadcast.getCreatedAt());
        assertEquals(returnedBroadcast.getCoordinates(), broadcast.getCoordinates());
        assertEquals(returnedBroadcast.getCourse(), broadcast.getCourse());
        assertEquals(returnedBroadcast.getDescription(), broadcast.getDescription());
    }

    @Test
    public void observeActiveBroadcasts_withNewlyCreatedAndOldBroadcast_observerIsCalledOnceWithRecentAndWithoutOldBroadcast() throws InterruptedException, ExecutionException, TimeoutException {
        UserId ownerId = new UserId("mrbob");
        MapCoordinates coordinates = new MapCoordinates(2, 2);
        String description = "test";
        CourseCode code = new CourseCode("dit111");

        Broadcast oldBroadcast = repository.store(new Broadcast(repository.nextIdentity(), ownerId, new Date(0), new Date(0), coordinates, code, description)).get(5, TimeUnit.SECONDS);
        Broadcast recentBroadcast = repository.store(new Broadcast(repository.nextIdentity(), ownerId, new Date(), new Date(), coordinates, code, description)).get(5, TimeUnit.SECONDS);

        CompletableFuture<List<Broadcast>> future = new CompletableFuture<>();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            LiveData<List<Broadcast>> observableBroadcasts = repository.observeActiveBroadcasts();
            observableBroadcasts.observeForever(new Observer<List<Broadcast>>() {
                @Override
                public void onChanged(List<Broadcast> broadcast) {
                    if (!broadcast.isEmpty()) { // Keep listening and waiting if no broadcasts came back yet
                        observableBroadcasts.removeObserver(this);
                        future.complete(broadcast);
                    }
                }
            });
        });

        List<Broadcast> returnedBroadcasts = future.get(5, TimeUnit.SECONDS);

        assertFalse("Old broadcast is not excluded", returnedBroadcasts.contains(oldBroadcast));
        assertTrue("Recent broadcast is not included", returnedBroadcasts.contains(recentBroadcast));
    }
}