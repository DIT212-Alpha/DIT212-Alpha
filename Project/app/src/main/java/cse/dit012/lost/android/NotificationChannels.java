package cse.dit012.lost.android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import cse.dit012.lost.R;
import cse.dit012.lost.android.service.ActiveBroadcastService;

/**
 * Constants and methods for setting up Android notification channels.
 * <p>
 * Author: Benjamin Sannholm
 * Used by: {@link ActiveBroadcastService}
 */
public final class NotificationChannels {
    public static final String NOTIFICATION_CHANNEL_ACTIVE_BROADCAST_ID = "active_broadcast";

    private NotificationChannels() {
    }

    /**
     * Creates a notification channel for notifications relating to a user's currently active broadcast.
     *
     * @param context the Android {@link Context} to create the notification channel in
     */
    public static void createActiveBroadcastNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ACTIVE_BROADCAST_ID,
                    context.getString(R.string.notification_channel_active_broadcast_name),
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription(context.getString(R.string.notification_channel_active_broadcast_description));
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            context.getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
    }
}
