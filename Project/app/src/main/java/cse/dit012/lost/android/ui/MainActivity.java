package cse.dit012.lost.android.ui;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import cse.dit012.lost.android.ui.screen.map.MapViewModel;
import cse.dit012.lost.databinding.ActivityMainBinding;

/**
 * MainActivity is used for launching the app
 * Author: Bashar Oumari
 */

public class MainActivity extends FragmentActivity  {

    private ActivityMainBinding mapScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapScreenBinding =  ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mapScreenBinding.getRoot());


    }

}