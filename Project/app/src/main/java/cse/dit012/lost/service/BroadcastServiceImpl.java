package cse.dit012.lost.service;

import java.util.Date;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;
import java9.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkNotNull;

final class BroadcastServiceImpl implements BroadcastService {
    private final BroadcastRepository broadcastRepository;

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
        return broadcastRepository.getById(id).thenCompose(broadcast -> {
            broadcast.updateLastActive();
            return broadcastRepository.store(broadcast);
        });
    }

    @Override
    public CompletableFuture<Broadcast> setBroadcastInactive(BroadcastId id) {
        return broadcastRepository.getById(id).thenCompose(broadcast -> {
            broadcast.setToInactive();
            return broadcastRepository.store(broadcast);
        });
    }

    @Override
    public CompletableFuture<Broadcast> editBroadcast(BroadcastId id, CourseCode course, String description) {
        return broadcastRepository.getById(id).thenCompose(broadcast -> {
            broadcast.updateCourse(course);
            broadcast.updateDescription(description);
            return broadcastRepository.store(broadcast);
        });
    }
}