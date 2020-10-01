package cse.dit012.lost.android.ui.screen.map;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.model.LatLng;

import cse.dit012.lost.R;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.service.BroadcastService;
import cse.dit012.lost.service.Gps;

public class AddBroadcastFragment extends Fragment {
    private static final String TAG = "AddBroadcastFragment";

    private Button addButton, cancelButton;
    private Spinner courseSpinner;
    private EditText descriptionEditText;

    public AddBroadcastFragment() {
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
                if(!courseSpinner.getSelectedItem().toString().isEmpty() && !descriptionEditText.getText().toString().isEmpty()) {
                    Context context = requireContext();
                    LatLng loc = Gps.getGps().getLocation(context);
                    CourseCode course = new CourseCode(courseSpinner.getSelectedItem().toString());

                    BroadcastService.get().createBroadcast(
                        new MapCoordinates(loc.latitude, loc.longitude),
                        course,
                        descriptionEditText.getText().toString()
                    ).whenComplete((broadcast, throwable) -> {
                        if (broadcast != null) {
                            BroadcastService.get().startActiveBroadcastService(context, broadcast.getId());
                            Toast.makeText(getActivity(), course + "\n" + descriptionEditText.getText().toString() + "\nadded", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e(TAG, "Failed to create broadcast", throwable);
                            Toast.makeText(getActivity(), "Failed to create broadcast :(", Toast.LENGTH_LONG).show();
                        }
                    }).exceptionally(throwable -> {
                        Log.e(TAG, "Failed to start active broadcast service", throwable);
                        return null;
                    });

                    //When the broadcast is added the user is taken back to the map view
                    navController.navigate(R.id.action_add_broadcast_fragment_to_mapScreenFragment);
                } else {
                    Toast.makeText(getActivity(), "select a course and set a description", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}