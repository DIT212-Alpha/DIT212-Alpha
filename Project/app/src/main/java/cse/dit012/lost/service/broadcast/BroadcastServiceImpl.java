package cse.dit012.lost.service.broadcast;

import java.util.Date;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;
import java9.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Broadcast service backed by broadcast repository.
 * <p>
 * Author: Benjamin Sannholm, Sophia Pham, Mathias Drage
 * Uses: {@link BroadcastService}, {@link BroadcastRepository}, {@link Broadcast}, {@link UserId},
 * {@link MapCoordinates}, {@link CourseCode}
 * Used by: {@link BroadcastServiceFactory}
 */
final class BroadcastServiceImpl implements BroadcastService {
    private final BroadcastRepository broadcastRepository;

    /**
     * Creates a {@link BroadcastServiceImpl} backed by the given broadcast repository.
     *
     * @param broadcastRepository the {@link BroadcastRepository} for the service to use
     */
    public BroadcastServiceImpl(BroadcastRepository broadcastRepository) {
        this.broadcastRepository = checkNotNull(broadcastRepository);
    }

    @Override
    public CompletableFuture<Broadcast> createBroadcast(UserId ownerUID, MapCoordinates coordinates, CourseCode courseCode, String description) {
        Broadcast broadcast = new Broadcast(
                broadcastRepository.nextIdentity(),
                ownerUID,
                new Date(),
                new Date(),
                coordinates,
                courseCode,
                description
        );
        return broadcastRepository.store(broadcast);
    }

    @Override
    public CompletableFuture<Broadcast> updateBroadcastLastActive(BroadcastId id) {
        // Retrieve, modify then store back broadcast
        return broadcastRepository.getById(id).thenCompose(broadcast -> {
            broadcast.updateLastActive();
            return broadcastRepository.store(broadcast);
        });
    }

    @Override
    public CompletableFuture<Broadcast> setBroadcastInactive(BroadcastId id) {
        // Retrieve, modify then store back broadcast
        return broadcastRepository.getById(id).thenCompose(broadcast -> {
            broadcast.setToInactive();
            return broadcastRepository.store(broadcast);
        });
    }

    @Override
    public CompletableFuture<Broadcast> editBroadcast(BroadcastId id, CourseCode course, String description) {
        // Retrieve, modify then store back broadcast
        return broadcastRepository.getById(id).thenCompose(broadcast -> {
            broadcast.updateCourse(course);
            broadcast.updateDescription(description);
            return broadcastRepository.store(broadcast);
        });
    }
}