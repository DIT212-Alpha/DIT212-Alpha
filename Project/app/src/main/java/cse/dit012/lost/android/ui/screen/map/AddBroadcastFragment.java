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
import cse.dit012.lost.android.service.ActiveBroadcastService;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;
import cse.dit012.lost.service.BroadcastService;
import cse.dit012.lost.service.GpsService;
import cse.dit012.lost.service.AuthenticatedUserService;

/**
 * View and controller for creating a broadcast.
 * Author: Pontus Nellgård
 * Used by: nav_graph.xml, fragment_add:broadcast_fragment.xml
 */
public final class AddBroadcastFragment extends Fragment {
    private static final String TAG = "AddBroadcastFragment";

    private EditText selectDateEditText, selectStartTimeEditText, selectEndTimeEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_broadcast_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Allows for navigation to another fragments
        //final NavController navController = Navigation.findNavController(view);

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
                LatLng loc = GpsService.getGps().getLocation(context);
                //Gets course from spinner
                CourseCode course = new CourseCode(courseSpinner.getSelectedItem().toString());
                //Gets user id from the logged in device
                UserId ownerUID = AuthenticatedUserService.get().getID();
                //Creates broadcast object
                BroadcastService.get().createBroadcast(
                        ownerUID,
                        new MapCoordinates(loc.latitude, loc.longitude),
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
                Toast.makeText(getActivity(), "select a course and set a description", Toast.LENGTH_LONG).show();
            }
        });
    /*    //set a date for the broadcast
        selectDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(selectDateEditText);
            }
        });

        //open time dialog for broadcast start
        selectStartTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(selectStartTimeEditText);
            }
        });

        //open time dialog for broadcast end
        selectEndTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(selectEndTimeEditText);
            }
        });

    }

    //Creates and opens a time dialog (used by both start and end time)
    private void showTimeDialog(EditText timeEditText) {
        Calendar cal = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                timeEditText.setText(simpleDateFormat.format(cal));
            }
        };
        new TimePickerDialog(getContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show();

    }

    //When the editTextField for date is pressed it triggers a datePickerDialog window
    public void showDateDialog(EditText selectDateEditText)
    {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("yy-MM-dd"));
                selectDateEditText.setText(simpleDateFormat.format(cal));
            }
        };
        new DatePickerDialog(getContext(), dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }*/
    }
}