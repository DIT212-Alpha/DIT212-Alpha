package cse.dit012.lost.android.ui.screen.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import cse.dit012.lost.Broadcast;
import cse.dit012.lost.Course;
import cse.dit012.lost.Database;
import cse.dit012.lost.Gps;
import cse.dit012.lost.R;
import cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment;
import cse.dit012.lost.databinding.FragmentMapScreenBinding;

public class MapScreenFragment extends Fragment {

    //private ActivityMapScreenBinding mapScreenBinding;
    FragmentMapScreenBinding mapScreenBinding;


    private MapViewModel model;
    AutoCompleteTextView textView;
    ArrayAdapter<String> adapter;
    Navigation navigation;
    Button broadCastBtn;
    FragmentManager fragmentManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mapScreenBinding = FragmentMapScreenBinding.inflate(inflater, container, false);

        model = new ViewModelProvider(getActivity()).get(MapViewModel.class);

       autoCompleteTextForCourses();

        return mapScreenBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View vieww, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(vieww, savedInstanceState);

        /**
         * Initialize the navigation controller and change the fragment on click
         */
        final NavController navController = Navigation.findNavController(vieww);
        broadCastBtn = vieww.findViewById(R.id.broadcast_btn);
        broadCastBtn.setOnClickListener(view -> navController.navigate(R.id.action_mapScreenFragment_to_add_broadcast_fragment));

    }



    /**
     * reads the input from user and send it to filter the chosen course on map
     */
    private void autoCompleteTextForCourses(){
        String[] arrayCourses = getResources().getStringArray(R.array.StringCourses);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayCourses);

        textView = mapScreenBinding.autoCompleteTextView;
        textView.setThreshold(1);
        textView.setTextColor(Color.BLACK);
        textView.setAdapter(adapter);
        textView.setHint(R.string.enter_course_to_filter);


        mapScreenBinding.autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            TextView v = mapScreenBinding.autoCompleteTextView;
            String coursName = v.getText().toString();

            model.setCourseCode(coursName);

        });

    }

}


