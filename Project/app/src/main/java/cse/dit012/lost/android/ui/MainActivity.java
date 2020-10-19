package cse.dit012.lost.android.ui;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import cse.dit012.lost.android.service.ActiveBroadcastService;
import cse.dit012.lost.databinding.ActivityMainBinding;

/**
 * MainActivity is used for launching the app.
 * <p>
 * Author: Bashar Oumari
 * Uses: res/layout/activity_main.xml
 * Used by: AndroidManifest.xml, {@link ActiveBroadcastService}
 */
public final class MainActivity extends FragmentActivity {
    private ActivityMainBinding mapScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapScreenBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mapScreenBinding.getRoot());
    }
}