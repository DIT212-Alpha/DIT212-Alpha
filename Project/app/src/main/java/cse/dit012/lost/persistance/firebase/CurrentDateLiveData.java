package cse.dit012.lost.persistance.firebase;

import android.os.Handler;

import androidx.lifecycle.LiveData;

import java.util.Date;

/**
 * An observable object that gives the current time at a set interval.
 * <p>
 * Author: Benjamin Sannholm
 * Used by: {@link FirebaseBroadcastRepository}
 */
final class CurrentDateLiveData extends LiveData<Date> {
    // Handler used to schedule update task periodically
    private final Handler handler = new Handler();

    // Task that runs at the given interval and updates the current LiveData value
    private final Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            setValue(new Date(System.currentTimeMillis()));
            handler.postDelayed(this, updateIntervalMs);
        }
    };

    // How often the current time should be updated
    private final long updateIntervalMs;

    /**
     * Creates a new {@link CurrentDateLiveData} with a given update interval.
     *
     * @param updateIntervalMs the interval to wait between updates, in milliseconds
     */
    public CurrentDateLiveData(long updateIntervalMs) {
        super(new Date(System.currentTimeMillis()));
        this.updateIntervalMs = updateIntervalMs;
    }

    @Override
    protected void onActive() {
        handler.post(updateTask);
    }

    @Override
    protected void onInactive() {
        handler.removeCallbacks(updateTask);
    }
}
