package cse.dit012.lost.android.ui.map;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cse.dit012.lost.Broadcast;
import cse.dit012.lost.R;
import cse.dit012.lost.databinding.FragmentBroadcastInfoWindowBinding;

public class BroadcastInfoWindowFragment extends Fragment {
    private static final String PARAM_COURSE = "course";
    private static final String PARAM_DESCRIPTION = "description";
    private Button editInfoWindowButton;
    private Button infoWindowCloseButton;
    //private AlertDialog.Builder dialogBuilder;
    //private AlertDialog dialog;

    private FragmentBroadcastInfoWindowBinding layoutBinding;

    private BroadcastInfoWindowViewModel model;

    public static BroadcastInfoWindowFragment newInstance(Broadcast broadcast) {
        BroadcastInfoWindowFragment fragment = new BroadcastInfoWindowFragment();

        Bundle args = new Bundle();
        args.putString(PARAM_COURSE, broadcast.getCourse().getName());
        args.putString(PARAM_DESCRIPTION, broadcast.getDescription());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        layoutBinding = FragmentBroadcastInfoWindowBinding.inflate(inflater, container, false);

        return layoutBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //dialogBuilder = new AlertDialog.Builder(this);
        //final View popupInfoBox = getLayoutInflater().inflate(R.layout.fragment_broadcast_info_window, null);

        model = new ViewModelProvider(getActivity()).get(BroadcastInfoWindowViewModel.class);

        String course = getArguments().getString(PARAM_COURSE);
        String description = getArguments().getString(PARAM_DESCRIPTION);
        layoutBinding.course.setText(course);
        layoutBinding.description.setText(description);

        infoWindowCloseButton = (Button) layoutBinding.infoWindowCloseButton;
        editInfoWindowButton = (Button) layoutBinding.editInfoWindowButton;

        //dialogBuilder.setView(popupInfoBox);
        //dialog = dialogBuilder.create();
        //dialog.show();

        infoWindowCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close View
                //dialog.dismiss();
                getActivity().getFragmentManager().popBackStack(); //ska stänga fönstret men funkar ej
            }
        });

        editInfoWindowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //editButton - Delete course and description and add EditText?
            }
        });



    }




}