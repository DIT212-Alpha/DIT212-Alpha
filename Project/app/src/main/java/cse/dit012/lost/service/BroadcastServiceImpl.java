package cse.dit012.lost.service;

import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import java.util.Date;

import cse.dit012.lost.android.service.ActiveBroadcastService;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.User;
import java9.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkNotNull;



class BroadcastServiceImpl implements BroadcastService {
    private final BroadcastRepository broadcastRepository;

    public BroadcastServiceImpl(BroadcastRepository broadcastRepository) {
        this.broadcastRepository = checkNotNull(broadcastRepository);
    }

    @Override
    public CompletableFuture<Broadcast> createBroadcast(User user, MapCoordinates coordinates, CourseCode courseCode, String description) {
        Broadcast broadcast = new Broadcast(user,broadcastRepository.nextIdentity(), new Date(), new Date(), coordinates, courseCode, description);
        return broadcastRepository.store(broadcast);
    }

    @Override
    public void startActiveBroadcastService(Context context, BroadcastId id) {
        Intent intent = new Intent(context, ActiveBroadcastService.class);
        intent.putExtra(ActiveBroadcastService.PARAM_BROADCAST_ID, id.toString());
        ContextCompat.startForegroundService(context, intent);
    }

    @Override
    public CompletableFuture<Broadcast> updateBroadcastLastActive(BroadcastId id) {
        return broadcastRepository.getById(id).thenCompose(broadcast -> {
            broadcast.updateLastActive();
            return broadcastRepository.store(broadcast);
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
