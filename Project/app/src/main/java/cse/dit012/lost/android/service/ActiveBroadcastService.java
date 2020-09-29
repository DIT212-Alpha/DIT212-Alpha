package cse.dit012.lost.android.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import cse.dit012.lost.R;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.service.BroadcastService;

import static com.google.common.base.Preconditions.checkNotNull;

public class ActiveBroadcastService extends Service {
    public static final String PARAM_BROADCAST_ID = "broadcast_id";

    private static final String ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_ID = "active_broadcast";
    private static final String ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_NAME = "Active Broadcast";
    private static final String ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_DESCRIPTION = "Notifications regarding your active broadcast";
    private static final int ACTIVE_BROADCAST_NOTIFICATION_ID = 1;

    private static final long KEEP_ALIVE_PERIOD_MS = 1000 * 10; // 30 seconds between keep alive checks

    private final Handler handler = new Handler();

    private BroadcastId activeBroadcastId;

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

    private static Notification createNotification(Context context) {
        return new NotificationCompat.Builder(context, ACTIVE_BROADCAST_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Ey, you're broadcasting!")
                .setContentText("BOOP")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }

    private void startInForeground() {
        createNotificationChannel(this);
        startForeground(ACTIVE_BROADCAST_NOTIFICATION_ID, createNotification(this));
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Service bind not used so return null
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("BROADCAST SERVICE CREATED!");
        startInForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkNotNull(intent.getStringExtra(PARAM_BROADCAST_ID), "Parameter " + PARAM_BROADCAST_ID + " is required");
        activeBroadcastId = new BroadcastId(intent.getStringExtra(PARAM_BROADCAST_ID));

        System.out.println("BROADCAST SERVICE STARTED FOR " + activeBroadcastId);
        handler.removeCallbacks(this::keepAliveBroadcast);
        handler.post(this::keepAliveBroadcast);

        return START_REDELIVER_INTENT;
    }

    private void keepAliveBroadcast() {
        System.out.println("BROADCAST " + activeBroadcastId + ": Keep alive!");
        BroadcastService.get().updateBroadcastLastActive(activeBroadcastId);

        handler.postDelayed(this::keepAliveBroadcast, KEEP_ALIVE_PERIOD_MS);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(this::keepAliveBroadcast);
    }
}
