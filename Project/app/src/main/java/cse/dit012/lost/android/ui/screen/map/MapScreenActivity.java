package cse.dit012.lost.android.ui.screen.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

import androidx.lifecycle.ViewModelProvider;
import cse.dit012.lost.Broadcast;
import cse.dit012.lost.Course;
import cse.dit012.lost.Database;
import cse.dit012.lost.Gps;
import cse.dit012.lost.R;
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