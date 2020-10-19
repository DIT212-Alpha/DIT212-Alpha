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

import cse.dit012.lost.R;
import cse.dit012.lost.android.service.ActiveBroadcastService;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;
import cse.dit012.lost.service.authenticateduser.AuthenticatedUserService;
import cse.dit012.lost.service.broadcast.BroadcastService;
import cse.dit012.lost.service.gps.GpsService;

/**
 * View and controller for creating a broadcast.
 * <p>
 * Author: Pontus Nellgård
 * Uses: res/layout/fragment_add_broadcast_fragment.xml, {@link ActiveBroadcastService}, {@link MapCoordinates},
 * {@link CourseCode}, {@link UserId}, {@link AuthenticatedUserService}, {@link BroadcastService}, {@link GpsService}
 * Used by: res/navigation/nav_graph.xml
 */
public final class AddBroadcastFragment extends Fragment {
    private static final String TAG = "AddBroadcastFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_broadcast_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Cancels this fragment
        Button cancelButton = view.findViewById(R.id.cancelBtn);
        //Finishes this fragment if broadcast is accepted
        Button addButton = view.findViewById(R.id.addBtn);
        //Spinner to scroll trough available courses
        Spinner courseSpinner = view.findViewById(R.id.courseSpinner);
        //Let user add a description as an input
        EditText descriptionEditText = view.findViewById(R.id.descriptionEdittext);

        cancelButton.setOnClickListener(view1 -> {
            //if cancelled, navigate back to mapscreenfragment
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_add_broadcast_fragment_to_mapScreenFragment);
        });

        addButton.setOnClickListener(v -> {
            if (!courseSpinner.getSelectedItem().toString().isEmpty() && !descriptionEditText.getText().toString().isEmpty()) {
                //Saves fragment context for the Gps call
                Context context = requireContext();
                //Get device location through the gps class
                MapCoordinates loc = GpsService.gps.getLocation(context);
                //Gets course from spinner
                CourseCode course = new CourseCode(courseSpinner.getSelectedItem().toString());
                //Gets user id from the logged in device
                UserId ownerUID = AuthenticatedUserService.userService.getID();
                //Creates broadcast object
                BroadcastService.INSTANCE.createBroadcast(
                        ownerUID,
                        loc,
                        course,
                        descriptionEditText.getText().toString()
                ).whenComplete((broadcast, throwable) -> {
                    if (broadcast != null) {
                        ActiveBroadcastService.startActiveBroadcastService(context, broadcast.getId());
                    } else {
                        Log.e(TAG, "Failed to create broadcast", throwable);
                        // TODO: Notify user (Toast errors in async callback. Since context is not available?)
                        //Toast.makeText(context, "Failed to create broadcast :(", Toast.LENGTH_LONG).show();
                    }
                }).exceptionally(throwable -> {
                    // TODO: Notify user
                    Log.e(TAG, "Failed to start active broadcast service", throwable);
                    return null;
                });

                //When the broadcast is added the user is taken back to the map view
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_add_broadcast_fragment_to_mapScreenFragment);
            } else {
                //If the requirements for creating a broadcast is not fulfilled
                Toast.makeText(getActivity(), "Please select a course and set a description", Toast.LENGTH_LONG).show();
            }
        });
    }
}