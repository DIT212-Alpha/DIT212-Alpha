package cse.dit012.lost.android.ui.screen.map;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import cse.dit012.lost.databinding.ActivityMapScreenBinding;

public class MapScreenActivity extends FragmentActivity {

    private ActivityMapScreenBinding mapScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapScreenBinding = ActivityMapScreenBinding.inflate(getLayoutInflater());
        setContentView(mapScreenBinding.getRoot());
    }
}