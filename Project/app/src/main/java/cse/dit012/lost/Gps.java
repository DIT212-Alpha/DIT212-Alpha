package cse.dit012.lost;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

public class Gps {
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean locationPermissionGranted;
    private LatLng location;
    private User user;
    private Activity activity;


    //Initialize in model by "LocationServices.getFusedLocationProviderClient(this) to access the phones gps
    public Gps(Activity a,FusedLocationProviderClient client,User u){
        fusedLocationProviderClient = client;
        user = u;
        activity = a;
    }
    public LatLng getLocation(){
        return new LatLng(location.latitude,location.longitude);
    }

    private void updateGPS(){
        //Checks if the user have granted the app access to the GPS
        if(ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //Access is granted, finding the location
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //Got access, update location.
                    updateLocation(location);
                }
            });
        }
        else{
            //Checks if build version handles permission requests
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions((Activity) activity,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_FINE_LOCATION);
            }
        }
    }

    private void updateLocation(Location newLocation) {
        location = new LatLng(newLocation.getLatitude(),newLocation.getLongitude());
    }
    private void updateUserLocation(){
        user.setLocation(location);
    }

    public void update(){
        updateGPS();
        updateUserLocation();
    }
}
