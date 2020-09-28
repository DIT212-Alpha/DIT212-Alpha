package cse.dit012.lost.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import cse.dit012.lost.Broadcast;
import cse.dit012.lost.Course;
import cse.dit012.lost.R;

public class add_broadcast_fragment extends Fragment {

    private Button addButton, cancelButton;
    private Spinner courseSpinner;
    private EditText descriptionEditText;

    public add_broadcast_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_broadcast_fragment, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        cancelButton = view.findViewById(R.id.cancelBtn);
        addButton = view.findViewById(R.id.addBtn);
        courseSpinner = view.findViewById(R.id.courseSpinner);
        descriptionEditText = view.findViewById(R.id.descriptionEdittext);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_add_broadcast_fragment_to_mapScreenFragment);
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!courseSpinner.getSelectedItem().toString().isEmpty() && !descriptionEditText.getText().toString().isEmpty()){

                    Toast.makeText(getActivity(), courseSpinner.getSelectedItem().toString() + "\n"+descriptionEditText.getText().toString(), Toast.LENGTH_LONG).show();
                    Course course = new Course(courseSpinner.getSelectedItem().toString());

                    try {
                        //Todo
                        // Gps.getGps.getLocation();

                        //Broadcast broadcastToAdd = new Broadcast(course, descriptionEditText.getText().toString(), Gps.getGps.getLocation().getLatitude(), Gps.getGps.getLocation().getLongitude());
                        Broadcast broadcastToAdd = new Broadcast(course, descriptionEditText.getText().toString(), 22, 44);

                        //Todo
                        // Add broadcast to DB

                        Toast.makeText(getActivity(), courseSpinner.getSelectedItem().toString() + "\n"+descriptionEditText.getText().toString()+"\nAdded", Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    //When the broadcast is added the user is taken back to the map view
                    navController.navigate(R.id.action_add_broadcast_fragment_to_mapScreenFragment);
                }
                else
                {
                    Toast.makeText(getActivity(), "select a course and set a description", Toast.LENGTH_LONG).show();
                }


                //When done adding a broadcast the view is changed back to the map
               // navController.navigate(R.id.action_add_broadcast_fragment_to_mapScreenFragment);
            }
        });

    }


}