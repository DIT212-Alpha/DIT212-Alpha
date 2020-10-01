package cse.dit012.lost.service;

import java.util.Date;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.course.CourseCode;
import java9.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkNotNull;

class BroadcastServiceImpl implements BroadcastService {
    private final BroadcastRepository broadcastRepository;

    public BroadcastServiceImpl(BroadcastRepository broadcastRepository) {
        this.broadcastRepository = checkNotNull(broadcastRepository);
    }

    @Override
    public Broadcast createBroadcast(MapCoordinates coordinates, CourseCode courseCode, String description) {
        Broadcast broadcast = new Broadcast(broadcastRepository.nextIdentity(), new Date(), coordinates, courseCode, description);
        broadcastRepository.store(broadcast);

        return broadcast;
    }

    @Override
    public CompletableFuture<Void> updateBroadcastLastActive(BroadcastId id) {
        return broadcastRepository.getById(id).thenAccept(broadcast -> {
            broadcast.updateLastActive();
            broadcastRepository.store(broadcast);
        });
    }

    @Override
    public CompletableFuture<Void> updateBroadcastEdit(BroadcastId id, CourseCode course, String description) {
        return broadcastRepository.getById(id).thenAccept(broadcast -> {
            broadcast.updateCourse(course);
            broadcast.updateDescription(description);
            broadcastRepository.store(broadcast);
        });
    }
}
