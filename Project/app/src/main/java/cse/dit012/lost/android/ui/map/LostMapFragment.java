package cse.dit012.lost.android.ui.map;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import cse.dit012.lost.R;
import cse.dit012.lost.android.ui.PermissionUtil;
import cse.dit012.lost.databinding.FragmentLostMapBinding;

public class LostMapFragment extends Fragment {

    private FragmentLostMapBinding lostMapBinding;

    private GoogleMap googleMap;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new RequestPermission(), this::onPermissionRequestResult);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        lostMapBinding = FragmentLostMapBinding.inflate(inflater, container, false);
        return lostMapBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeGoogleMap();
    }

    private void initializeGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::onGoogleMapReady);
        }
    }

    private void onGoogleMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        requestGeolocationPermissions();
    }

    private void requestGeolocationPermissions() {
        if (!PermissionUtil.hasPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            enableGeolocationDependentFeatures();
        }
    }

    private void onPermissionRequestResult(boolean granted) {
        if (granted) {
            enableGeolocationDependentFeatures();
        }
    }

    @SuppressLint("MissingPermission")
    private void enableGeolocationDependentFeatures() {
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lostMapBinding = null;
    }
}