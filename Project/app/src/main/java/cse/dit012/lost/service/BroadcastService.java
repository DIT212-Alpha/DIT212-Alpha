package cse.dit012.lost.service;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.course.CourseCode;
import java9.util.concurrent.CompletableFuture;

public interface BroadcastService {
    static BroadcastService create() {
        return new BroadcastServiceImpl(BroadcastRepository.create());
    }

    /**
     * Creates a new broadcast placed at the given coordinates for a specific course and with a given description.
     * @param coordinates coordinates of broadcast
     * @param courseCode the course code of the course the broadcast is for
     * @param description the description of the broadcast
     * @return the newly created broadcast
     */
    Broadcast createBroadcast(MapCoordinates coordinates, CourseCode courseCode, String description);

    CompletableFuture<Void> updateBroadcastLastActive(BroadcastId id);

    CompletableFuture<Void> updateBroadcastEdit(BroadcastId id, CourseCode course, String description);
}