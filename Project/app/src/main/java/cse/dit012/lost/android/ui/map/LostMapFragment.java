package cse.dit012.lost.android.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import cse.dit012.lost.Broadcast;
import cse.dit012.lost.R;
import cse.dit012.lost.android.ui.PermissionUtil;
import cse.dit012.lost.android.ui.screen.map.MapViewModel;
import cse.dit012.lost.databinding.FragmentLostMapBinding;

public class LostMapFragment extends Fragment {

    private FragmentLostMapBinding lostMapBinding;


    private MapInfoWindowFragment mapFragment;
    private GoogleMap googleMap;

    private MapViewModel model;

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

        model = new ViewModelProvider(getActivity()).get(MapViewModel.class);

        initializeGoogleMap();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lostMapBinding = null;
    }

    private void initializeGoogleMap() {
        mapFragment = (MapInfoWindowFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::onGoogleMapReady);
        }
    }

    private void onGoogleMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        requestGeolocationPermissions();

        setupBroadcastsOnMap();
    }

    private void requestGeolocationPermissions() {
        if (!PermissionUtil.hasPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            enableGeolocationDependentFeatures();
            onLocationPermessionAndMapReady();
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
        gotoCurrentLocation();
    }

    @SuppressLint("MissingPermission")
    private void gotoCurrentLocation() {

            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(getActivity(), task -> {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    if (location != null) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(location.getLatitude(),
                                        location.getLongitude()), 15));
                    }

                }
            });
    }


    private void setupBroadcastsOnMap() {



        model.getActiveBroadcasts().observe(getActivity(), broadcasts -> {
            googleMap.clear();

            for (Broadcast broadcast : broadcasts) {
                if (broadcast.getCourse().getName().equals(model.getCurrentName().getValue())) {
                    LatLng pos = new LatLng(broadcast.getLatitude(), broadcast.getLongitude());
                    Marker marker = googleMap.addMarker(new MarkerOptions()
                            .position(pos)
                            .title(broadcast.getCourse().getName()));
                    marker.setTag(broadcast);
                }
            }
        });

        googleMap.setOnMarkerClickListener(marker -> {
            Broadcast broadcast = (Broadcast) marker.getTag();

            InfoWindow.MarkerSpecification markerSpec = new InfoWindow.MarkerSpecification(0, 70);
            BroadcastInfoWindowFragment windowFragment = BroadcastInfoWindowFragment.newInstance(broadcast);
            InfoWindow infoWindow = new InfoWindow(marker, markerSpec, windowFragment);
            mapFragment.infoWindowManager().show(infoWindow, true);

            return true;
        });
    }





}