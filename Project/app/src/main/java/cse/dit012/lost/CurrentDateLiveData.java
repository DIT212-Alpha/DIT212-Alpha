package cse.dit012.lost;

import android.os.Handler;

import androidx.lifecycle.LiveData;

import java.util.Date;

class CurrentDateLiveData extends LiveData<Date> {
    private final Handler handler = new Handler();

    private final Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            setValue(new Date(System.currentTimeMillis()));
            handler.postDelayed(this, 1_000);
        }
    };

    public CurrentDateLiveData() {
        super(new Date(System.currentTimeMillis()));
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
