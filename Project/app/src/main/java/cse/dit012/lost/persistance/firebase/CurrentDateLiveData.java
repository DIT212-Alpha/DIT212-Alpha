package cse.dit012.lost.persistance.firebase;

import android.os.Handler;

import androidx.lifecycle.LiveData;

import java.util.Date;

/**
 * An observable object that gives the current time at a set interval.
 * Author: Benjamin Sannholm
 */
final class CurrentDateLiveData extends LiveData<Date> {
    private final Handler handler = new Handler();

    private final Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            setValue(new Date(System.currentTimeMillis()));
            handler.postDelayed(this, updateFrequencyMs);
        }
    };

    private final long updateFrequencyMs;

    public CurrentDateLiveData(long updateFrequencyMs) {
        super(new Date(System.currentTimeMillis()));
        this.updateFrequencyMs = updateFrequencyMs;
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
