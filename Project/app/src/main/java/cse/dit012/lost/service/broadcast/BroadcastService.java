package cse.dit012.lost.service.broadcast;

import cse.dit012.lost.android.service.ActiveBroadcastService;
import cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment;
import cse.dit012.lost.android.ui.screen.map.AddBroadcastFragment;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;
import java9.util.concurrent.CompletableFuture;

/**
 * Service responsible for preforming tasks related to broadcasts.
 * <p>
 * Author: Benjamin Sannholm, Sophia Pham, Mathias Drage
 * Uses: {@link BroadcastServiceFactory}, {@link Broadcast}, {@link UserId}, {@link MapCoordinates}, {@link CourseCode},
 * Used by: {@link ActiveBroadcastService}, {@link AddBroadcastFragment}, {@link BroadcastInfoWindowFragment},
 * {@link BroadcastServiceFactory}, {@link BroadcastServiceImpl}
 */
public interface BroadcastService {
    /**
     * @return Singleton object
     */
    BroadcastService INSTANCE = BroadcastServiceFactory.get();

    /**
     * Creates a new broadcast placed at the given coordinates, owned by a given user,
     * for a specific course and with a given description.
     *
     * @param userId      the {@link UserId} of user who owns the broadcast
     * @param coordinates coordinates of broadcast
     * @param courseCode  the course code of the course the broadcast is for
     * @param description the description of the broadcast
     * @return the newly created broadcast
     */
    CompletableFuture<Broadcast> createBroadcast(UserId userId, MapCoordinates coordinates, CourseCode courseCode, String description);

    /**
     * Updates the time a broadcast was last active to the current time.
     *
     * @param id the {@link BroadcastId} of the broadcast to update
     * @return the {@link Broadcast} after it has been updated
     */
    CompletableFuture<Broadcast> updateBroadcastLastActive(BroadcastId id);

    /**
     * Sets the given broadcast to an inactive state.
     *
     * @param id the {@link BroadcastId} of the broadcast to update
     * @return the {@link Broadcast} after it has been updated
     */
    CompletableFuture<Broadcast> setBroadcastInactive(BroadcastId id);

    /**
     * Edits the course and description of the given broadcast.
     *
     * @param id          the {@link BroadcastId} of the broadcast to update
     * @param course      the {@link CourseCode} of the new course
     * @param description the new description
     * @return the {@link Broadcast} after it has been updated
     */
    CompletableFuture<Broadcast> editBroadcast(BroadcastId id, CourseCode course, String description);
}