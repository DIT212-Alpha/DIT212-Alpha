package cse.dit012.lost.android.ui.map;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

import cse.dit012.lost.R;
import cse.dit012.lost.android.ui.PermissionUtil;
import cse.dit012.lost.databinding.FragmentLostMapBinding;

public class LostMapFragment extends Fragment {

    private FragmentLostMapBinding lostMapBinding;

    private GoogleMap googleMap;
    Location location;


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
            onLocationPermessionAndMapReady();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        try {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(getActivity(), task -> {
                if (task.isSuccessful()) {
                    location = task.getResult();
                    if (location != null) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(location.getLatitude(),
                                        location.getLongitude()), 15));
                    }

                }
            });
        }catch (Exception e){
            e.getMessage();
        }
    }


    private void onPermissionRequestResult(boolean granted) {
        if (granted) {
            enableGeolocationDependentFeatures();
            onLocationPermessionAndMapReady();
        }
    }

    @SuppressLint("MissingPermission")
    private void enableGeolocationDependentFeatures() {
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    private void onLocationPermessionAndMapReady(){
        getCurrentLocation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lostMapBinding = null;
    }
}