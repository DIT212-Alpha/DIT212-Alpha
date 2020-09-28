package cse.dit012.lost;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import cse.dit012.lost.android.PermissionUtil;
import cse.dit012.lost.android.ui.map.LostMapFragment;

public class Gps {

    private LatLng location;
    private User user;
    private LocationManager locationManager;
    private LostMapFragment lostMapFragment;
    private LocationListener locationListener;

    //Initialize in model by "LocationServices.getFusedLocationProviderClient(this) to access the phones gps
    public Gps(LostMapFragment f, User u,LocationManager locationManager) {
        user = u;
        lostMapFragment = f;
        this.locationManager = locationManager;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(location != null) {
                    updateLocation(location);
                    updateUserLocation();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

    private void updateLocation(Location newLocation) {
        location = new LatLng(newLocation.getLatitude(), newLocation.getLongitude());
    }

    private void updateUserLocation() {
        user.setLocation(location);
    }

    public void startGps() {
        if (ActivityCompat.checkSelfPermission(lostMapFragment.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(lostMapFragment.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           lostMapFragment.requestGeolocationPermissions();
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

    }
}
