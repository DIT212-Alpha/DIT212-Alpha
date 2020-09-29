package cse.dit012.lost.android.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import cse.dit012.lost.android.service.ActiveBroadcastService;

public class ActiveBroadcastServiceTestActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        {
            Intent intent = new Intent(this, ActiveBroadcastService.class);
            intent.putExtra(ActiveBroadcastService.PARAM_BROADCAST_ID, "testBroadcast");
            ContextCompat.startForegroundService(this, intent);
        }
        {
            Intent intent = new Intent(this, ActiveBroadcastService.class);
            intent.putExtra(ActiveBroadcastService.PARAM_BROADCAST_ID, "broadcast6");
            ContextCompat.startForegroundService(this, intent);
        }
        {
            Intent intent = new Intent(this, ActiveBroadcastService.class);
            intent.putExtra(ActiveBroadcastService.PARAM_BROADCAST_ID, "broadcast5");
            ContextCompat.startForegroundService(this, intent);
        }
    }
}