package cse.dit012.lost.android.ui.screen.map;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import cse.dit012.lost.R;
import cse.dit012.lost.databinding.FragmentMapScreenBinding;
import cse.dit012.lost.service.MailAndPasswordLoginService;

/**
 * This class shows the map and contains the autocomplete text box for course filtration,
 * and the button broadcast to add a broadcast.
 * Author: Bashar Oumari, Benjamin Sannholm
 */
public final class MapScreenFragment extends Fragment {
    private FragmentMapScreenBinding mapScreenBinding;

    private MapViewModel model;

    private final MailAndPasswordLoginService mailAndPasswordLoginService = new MailAndPasswordLoginService();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mapScreenBinding = FragmentMapScreenBinding.inflate(inflater, container, false);
        return mapScreenBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View vieww, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(vieww, savedInstanceState);

        model = new ViewModelProvider(getActivity()).get(MapViewModel.class);

        autoCompleteTextForCourses();

        // Initialize the navigation controller and change the fragment on click
        final NavController navController = Navigation.findNavController(vieww);
        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.mapScreenFragment, true).build();

        mapScreenBinding.broadcastBtn.setOnClickListener(view ->
                navController.navigate(R.id.action_mapScreenFragment_to_add_broadcast_fragment));


        mapScreenBinding.signOutBtn.setOnClickListener(view -> {
            mailAndPasswordLoginService.signOutUser();
            navController.navigate(R.id.action_mapScreenFragment_to_loginFragment, null, navOptions);
        });
    }

    /**
     * reads the input from user and send it to filter the chosen course on map
     */
    private void autoCompleteTextForCourses() {
        String[] arrayCourses = getResources().getStringArray(R.array.StringCourses);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, arrayCourses);
        mapScreenBinding.courseFilterTextbox.setAdapter(adapter);

        mapScreenBinding.courseFilterTextbox.addTextChangedListener(new TextWatcher() {
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