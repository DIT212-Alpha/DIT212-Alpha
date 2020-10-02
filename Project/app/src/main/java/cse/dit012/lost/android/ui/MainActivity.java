package cse.dit012.lost.android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import cse.dit012.lost.R;
import cse.dit012.lost.android.ui.screen.map.MapViewModel;
import cse.dit012.lost.databinding.ActivityMainBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

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