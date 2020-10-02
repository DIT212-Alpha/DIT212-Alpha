package cse.dit012.lost.persistance.firebase;

import android.os.Handler;

import androidx.lifecycle.LiveData;

import java.util.Date;

/**
 * This class represent an observable object that gives the current time
 * AUTHOR: Benjamin Sannholm
 */

class CurrentDateLiveData extends LiveData<Date> {
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
        handler.postDelayed(updateTask,0);
    }

    @Override
    protected void onInactive() {
        handler.removeCallbacks(updateTask);
    }
}
