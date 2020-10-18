package cse.dit012.lost.android.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;

import cse.dit012.lost.R;
import cse.dit012.lost.android.NotificationChannels;
import cse.dit012.lost.android.ui.MainActivity;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.MapUtil;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.service.BroadcastService;
import cse.dit012.lost.service.GpsService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class takes a broadcast and tells the database periodically that the current broadcast is still there.
 * Author: Benjamin Sannholm
 */
public final class ActiveBroadcastService extends LifecycleService {
    private static final String TAG = "ActiveBroadcastService";

    private static final String PARAM_BROADCAST_ID = "broadcast_id";

    private static final int ACTIVE_BROADCAST_NOTIFICATION_ID = 1;

    private static final long KEEP_ALIVE_PERIOD_MS = 1000 * 20; // 20 seconds between keep alive checks

    // Used to schedule periodic keep alive checks
    private final Handler handler = new Handler();

    private LiveData<Broadcast> currentBroadcast;

    /**
     * Starts the active broadcast service for broadcast with ID {@code id}.
     * If the service is already running, the service switches to keep the new broadcast active instead.
     *
     * @param context Android {@link Context} to start the service in
     * @param id      {@link BroadcastId} of the broadcast to be kept active
     */
    public static void startActiveBroadcastService(Context context, BroadcastId id) {
        Intent intent = new Intent(context, ActiveBroadcastService.class);
        intent.putExtra(ActiveBroadcastService.PARAM_BROADCAST_ID, id.toString());
        ContextCompat.startForegroundService(context, intent);
    }

    /**
     * Creates the notification that is permanently displayed while the user keeps a broadcast active.
     *
     * @param context   Android {@link Context} to create the {@link Notification} in
     * @param broadcast the {@link Broadcast} that the notification should display
     * @return the created {@link Notification}
     */
    private static Notification createNotification(Context context, Broadcast broadcast) {
        // Actions to be executed for interactions with the notification
        PendingIntent openAppPendingIntent = PendingIntent.getActivity(
                context,
                0,
                new Intent(context, MainActivity.class),
                0
        );

        Intent stopBroadcastIntent = new Intent(context, NotificationActionsReceiver.class);
        if (broadcast != null) {
            stopBroadcastIntent.putExtra(NotificationActionsReceiver.PARAM_BROADCAST_ID, broadcast.getId().toString());
        }
        PendingIntent stopBroadcastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                stopBroadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Build the notification
        return new NotificationCompat.Builder(context, NotificationChannels.NOTIFICATION_CHANNEL_ACTIVE_BROADCAST_ID)
                // Contents
                .setContentTitle(context.getString(R.string.notification_active_broadcast_title))
                .setContentText(broadcast != null ?
                        broadcast.getCourse().toString() :
                        context.getString(R.string.loading_with_ellipsis)
                )
                .setShowWhen(false) // Don't show time
                // Visuals
                .setSmallIcon(R.drawable.ic_launcher_foreground) // TODO: Change icon
                .setColorized(true)
                .setColor(ContextCompat.getColor(context, R.color.notification_active_broadcast_background))
                .setStyle(
                        new androidx.media.app.NotificationCompat.MediaStyle()
                                .setShowActionsInCompactView(0) // Stop button
                                .setShowCancelButton(true)
                                .setCancelButtonIntent(stopBroadcastPendingIntent)
                )
                // Behavior
                .setContentIntent(openAppPendingIntent)
                .addAction(
                        R.drawable.common_full_open_on_phone, // TODO: Change icon
                        context.getString(R.string.notification_active_broadcast_stop),
                        stopBroadcastPendingIntent
                )
                // Misc
                .setPriority(NotificationCompat.PRIORITY_LOW) // Needed for older Android versions without notification channels
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Needed for older Android versions without notification channels
                .build();
    }

    /**
     * Starts the service in the foreground, which means it displays a non-removable notification
     * displaying that it is running.
     */
    private void startInForeground() {
        NotificationChannels.createActiveBroadcastNotificationChannel(this);
        Notification notification = createNotification(this, null);
        startForeground(ACTIVE_BROADCAST_NOTIFICATION_ID, notification);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Broadcast service created");
        startInForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        // Handle input parameters
        checkNotNull(intent.getStringExtra(PARAM_BROADCAST_ID), "Parameter " + PARAM_BROADCAST_ID + " is required");
        BroadcastId activeBroadcastId = new BroadcastId(intent.getStringExtra(PARAM_BROADCAST_ID));

        // Start listening for changes to currently active broadcast
        setupActiveBroadcastLiveData(activeBroadcastId);

        // Remove any potentially previously queued calls to keepAliveBroadcast and queue it again
        handler.removeCallbacksAndMessages(null);
        handler.post(this::keepAliveBroadcast);

        Log.d(TAG, "Broadcast service started for: " + activeBroadcastId);

        return START_REDELIVER_INTENT;
    }

    /**
     * Starts listening for changes to the broadcast corresponding to the given {@link BroadcastId}.
     *
     * @param activeBroadcastId the {@link BroadcastId} of the active broadcast
     */
    private void setupActiveBroadcastLiveData(BroadcastId activeBroadcastId) {
        if (currentBroadcast != null) {
            currentBroadcast.removeObservers(this);
        }
        currentBroadcast = BroadcastRepository.get().observeById(activeBroadcastId);
        currentBroadcast.observe(this, this::onBroadcastUpdated);
    }

    /**
     * Callback for when the currently active broadcast changes.
     *
     * @param broadcast the new {@link Broadcast} with updates values
     */
    private void onBroadcastUpdated(Broadcast broadcast) {
        Log.v(TAG, "Broadcast " + broadcast.getId() + " changed");

        // Update notification with latest information
        Notification notification = createNotification(this, broadcast);
        NotificationManagerCompat.from(this).notify(ACTIVE_BROADCAST_NOTIFICATION_ID, notification);
    }

    /**
     * Runs periodically to check if the currently active broadcast should be kept alive.
     * If the user is still in range of the broadcast, it is kept alive.
     * If not, the service is stopped.
     */
    private void keepAliveBroadcast() {
        if (currentBroadcast.getValue() != null) {
            // Fetch user's current coordinates
            MapCoordinates currentCoords = GpsService.getGps().getLocation(this);

            Log.v(TAG, "Distance to " + currentBroadcast.getValue().getId() + ": " + MapUtil.distanceBetweenPoints(currentCoords, currentBroadcast.getValue().getCoordinates()) + " meters");
            if (currentBroadcast.getValue().isPointInRangeOfBroadcast(currentCoords)) {
                Log.v(TAG, "Broadcast " + currentBroadcast.getValue().getId() + ": In range so keep alive!");

                // Keep alive broadcast
                BroadcastService.get().updateBroadcastLastActive(currentBroadcast.getValue().getId()).exceptionally(throwable -> {
                    Log.w(TAG, "Failed to keep alive broadcast", throwable);
                    return null;
                });
            } else {
                Log.d(TAG, "Broadcast " + currentBroadcast.getValue().getId() + ": Out of range!");
                // Stop service
                stopSelf();
            }
        }

        // Run same function again after a delay
        handler.postDelayed(this::keepAliveBroadcast, KEEP_ALIVE_PERIOD_MS);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
        Log.d(TAG, "Broadcast service destroyed");
    }

    /**
     * Android {@link BroadcastReceiver} handling interactions with the active broadcast notification.
     */
    public final static class NotificationActionsReceiver extends BroadcastReceiver {
        public static final String PARAM_BROADCAST_ID = "broadcast_id";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.hasExtra(PARAM_BROADCAST_ID)) {
                return;
            }

            // When stop button is pressed
            Log.d(TAG, "Stopping broadcast service");
            context.stopService(new Intent(context, ActiveBroadcastService.class));
            BroadcastService.get().setBroadcastInactive(new BroadcastId(intent.getStringExtra(PARAM_BROADCAST_ID)));
        }
    }
}