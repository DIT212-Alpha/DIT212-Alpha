package cse.dit012.lost.service;

import cse.dit012.lost.BroadcastRepositoryProvider;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;
import java9.util.concurrent.CompletableFuture;

/**
 * Service responsible for preforming tasks related to broadcasts.
 * Author: Benjamin Sannholm, Sophia Pham
 */
public interface BroadcastService {
    /**
     * Gives an instance of the broadcast service.
     *
     * @return an instance of the {@link BroadcastService}
     */
    static BroadcastService get() {
        return fromRepository(BroadcastRepositoryProvider.get());
    }

    /**
     * Gives an instance of the broadcast service backed by the given broadcast repository.
     *
     * @param repository the {@link BroadcastRepository} to use
     * @return an instance of the {@link BroadcastService}
     */
    static BroadcastService fromRepository(BroadcastRepository repository) {
        return new BroadcastServiceImpl(repository);
    }

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