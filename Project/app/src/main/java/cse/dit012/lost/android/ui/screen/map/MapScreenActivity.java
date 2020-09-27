package cse.dit012.lost.android.ui.screen.map;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import cse.dit012.lost.databinding.ActivityMapScreenBinding;

public class MapScreenActivity extends FragmentActivity {

    private ActivityMapScreenBinding mapScreenBinding;

    private MapViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapScreenBinding = ActivityMapScreenBinding.inflate(getLayoutInflater());
        setContentView(mapScreenBinding.getRoot());

        model = new ViewModelProvider(this).get(MapViewModel.class);
    }



    @Override
    protected void onStart() {
        super.onStart();




    }
}