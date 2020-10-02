package cse.dit012.lost.android.ui.screen.map;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import cse.dit012.lost.R;
import cse.dit012.lost.databinding.FragmentMapScreenBinding;

/**
 * This class shows the map and contains the autocomplete text box for course filtration, and the button broadcast to add broadcast
 * Author: Bashar Oumari, Benjamin Sannholm
 */

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


        mapScreenBinding.autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                model.setCourseCode(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

}


