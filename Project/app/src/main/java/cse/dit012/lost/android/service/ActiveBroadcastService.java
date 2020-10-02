package cse.dit012.lost.android.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;

import cse.dit012.lost.R;
import cse.dit012.lost.android.ui.MainActivity;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;
import cse.dit012.lost.service.BroadcastService;

import static com.google.common.base.Preconditions.checkNotNull;

// TODO: Move all strings to strings.xml

/**
 * This class takes a broadcast and tells the database periodically that the current broadcast is still there.
 * AUTHOR: Benjamin Sannholm
 */

public class ActiveBroadcastService extends LifecycleService {
    private static final String TAG = "ActiveBroadcastService";

    public static final String PARAM_BROADCAST_ID = "broadcast_id";

    private static final String ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_ID = "active_broadcast";
    private static final String ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_NAME = "Active Broadcast";
    private static final String ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_DESCRIPTION = "Notifications regarding your active broadcast";
    private static final int ACTIVE_BROADCAST_NOTIFICATION_ID = 1;

    private static final long KEEP_ALIVE_PERIOD_MS = 1000 * 20; // 20 seconds between keep alive checks

    public static class NotificationActionsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Stopping broadcast service");
            context.stopService(new Intent(context, ActiveBroadcastService.class));
        }
    }

    private final BroadcastRepository broadcastRepository = BroadcastRepository.get();

    private final Handler handler = new Handler();

    private BroadcastId activeBroadcastId;
    private LiveData<Broadcast> currentBroadcast;

    private static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_ID,
                    ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription(ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_DESCRIPTION);
            context.getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
    }

    private static Notification createNotification(Context context, Broadcast broadcast) {
        PendingIntent openAppIntent = PendingIntent.getActivity(context,
                0,
                new Intent(context, MainActivity.class),
                0);
        PendingIntent stopBroadcastIntent = PendingIntent.getBroadcast(context,
                0,
                new Intent(context, NotificationActionsReceiver.class),
                0);

        return new NotificationCompat.Builder(context, ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_ID)
                // Contents
                .setContentTitle("You're broadcasting!")
                .setContentText(broadcast != null ? broadcast.getCourse().toString() : "Loading...")
                .setShowWhen(false)
                // Visuals
                .setSmallIcon(R.drawable.ic_launcher_foreground) // TODO: Change icon
                .setColorized(true)
                .setColor(ContextCompat.getColor(context, R.color.notification_active_broadcast_background))
                .setStyle(
                    new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0)
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(stopBroadcastIntent)
                )
                // Behavior
                .setContentIntent(openAppIntent)
                .addAction(R.drawable.common_full_open_on_phone, "Stop broadcast", stopBroadcastIntent) // TODO: Change icon
                // Misc
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();
    }

    private void startInForeground() {
        createNotificationChannel(this);
        Notification notification = createNotification(this, null);
        startForeground(ACTIVE_BROADCAST_NOTIFICATION_ID, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        // Service bind not used so return null
        return null;
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

        checkNotNull(intent.getStringExtra(PARAM_BROADCAST_ID), "Parameter " + PARAM_BROADCAST_ID + " is required");
        activeBroadcastId = new BroadcastId(intent.getStringExtra(PARAM_BROADCAST_ID));

        setupActiveBroadcastLiveData();

        handler.removeCallbacksAndMessages(null); // Remove any previously queued calls to keepAliveBroadcast
        handler.post(this::keepAliveBroadcast);

        Log.d(TAG, "Broadcast service started for: " + activeBroadcastId);

        return START_REDELIVER_INTENT;
    }

    private void setupActiveBroadcastLiveData() {
        if (currentBroadcast != null) {
            currentBroadcast.removeObservers(this);
        }
        currentBroadcast = broadcastRepository.observeById(activeBroadcastId);
        currentBroadcast.observe(this, this::onBroadcastUpdated);
    }

    private void onBroadcastUpdated(Broadcast broadcast) {
        Log.v(TAG, "Broadcast " + activeBroadcastId + " changed");

        // Update notification with latest information
        Notification notification = createNotification(this, broadcast);
        NotificationManagerCompat.from(this).notify(ACTIVE_BROADCAST_NOTIFICATION_ID, notification);
    }

    private void keepAliveBroadcast() {
        Log.v(TAG, "Broadcast " + activeBroadcastId + ": Keep alive!");

        BroadcastService.get().updateBroadcastLastActive(activeBroadcastId).exceptionally(throwable -> {
            Log.w(TAG, "Failed to keep alive broadcast", throwable);
            return null;
        });

        // Run same function again after a delay
        handler.postDelayed(this::keepAliveBroadcast, KEEP_ALIVE_PERIOD_MS);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
        Log.d(TAG, "Broadcast service destroyed");
    }
}
