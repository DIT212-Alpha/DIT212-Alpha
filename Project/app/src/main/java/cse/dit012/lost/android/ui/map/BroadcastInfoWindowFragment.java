package cse.dit012.lost.android.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cse.dit012.lost.databinding.FragmentBroadcastInfoWindowBinding;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.service.BroadcastService;

/**
 * A fragment for the contents of the information popup shown when a broadcast is pressed on the map.
 * Author: Sophia Pham
 */
public final class BroadcastInfoWindowFragment extends Fragment {
    private static final String PARAM_COURSE = "course";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_ID = "id";

    // View Binding for layout file
    private FragmentBroadcastInfoWindowBinding layoutBinding;

    /**
     * Creates a new broadcast info window fragment displaying the information of the given {@link Broadcast}.
     *
     * @param broadcast the broadcast to be displayed
     * @return the created broadcast info window fragment
     */
    public static BroadcastInfoWindowFragment newInstance(Broadcast broadcast) {
        BroadcastInfoWindowFragment fragment = new BroadcastInfoWindowFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_COURSE, broadcast.getCourse().toString());
        args.putString(PARAM_DESCRIPTION, broadcast.getDescription());
        args.putString(PARAM_ID, broadcast.getId().toString());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Setup view from layout file
        layoutBinding = FragmentBroadcastInfoWindowBinding.inflate(inflater, container, false);
        return layoutBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Hide the EditText in InfoWindow
        layoutBinding.editViewInfoBox.setVisibility(View.GONE);

        // Update text fields to display info about broadcast given in parameters
        String course = getArguments().getString(PARAM_COURSE);
        String description = getArguments().getString(PARAM_DESCRIPTION);
        String id = getArguments().getString(PARAM_ID);
        layoutBinding.course.setText(course);
        layoutBinding.description.setText(description);
        layoutBinding.editCourseText.setText(course);
        layoutBinding.editDescriptionText.setText(description);

        //EDIT button: Makes it possible for the user to Edit Course and Description
        layoutBinding.editInfoWindowButton.setOnClickListener(v -> {
            layoutBinding.editCourseText.setText(layoutBinding.course.getText());
            layoutBinding.editDescriptionText.setText(layoutBinding.description.getText());
            layoutBinding.textViewInfoBox.setVisibility(View.GONE);
            layoutBinding.editViewInfoBox.setVisibility(View.VISIBLE);
            //TODO restore saved data
        });

        //SAVE button: Saves the edit
        layoutBinding.saveInfoWindowButton.setOnClickListener(v -> {
            //TODO should save the edit into current TextView
            String courseEdited = layoutBinding.editCourseText.getText().toString().trim();
            String descriptionEdited = layoutBinding.editDescriptionText.getText().toString().trim();
            layoutBinding.course.setText(courseEdited);
            layoutBinding.description.setText(descriptionEdited);

            //TODO should save it in the database as well and change the variables there

            BroadcastService.get().updateBroadcastEdit(new BroadcastId(id), new CourseCode(courseEdited), descriptionEdited);

            layoutBinding.editViewInfoBox.setVisibility(View.GONE);
            layoutBinding.textViewInfoBox.setVisibility(View.VISIBLE);
        });

        //CANCEL button: Cancels the edit
        layoutBinding.cancelInfoWindowButton.setOnClickListener(v -> {
            layoutBinding.editViewInfoBox.setVisibility(View.GONE);
            layoutBinding.textViewInfoBox.setVisibility(View.VISIBLE);
        });
    }
}