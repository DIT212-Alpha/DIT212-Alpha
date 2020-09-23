package cse.dit012.lost;

import android.Manifest;
import android.app.Activity;
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

public class Gps extends AppCompatActivity {
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng location = new LatLng(-33.8523341, 151.2106085);
    private boolean locationPermissionGranted;


    //Initialize in model by "LocationServices.getFusedLocationProviderClient(this) to access the phones gps
    public Gps(FusedLocationProviderClient client){
        fusedLocationProviderClient = client;
    }
    public LatLng getLocation(){
        return new LatLng(location.latitude,location.longitude);
    }

    public void updateGPS(Activity context){
        //Checks if the user have granted the app access to the GPS
        if(ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //Access is granted, finding the location
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(context, new OnSuccessListener<Location>() {
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
                ActivityCompat.requestPermissions((Activity) context,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_FINE_LOCATION);
            }
        }
    }

    private void updateLocation(Location newLocation) {
        location = new LatLng(newLocation.getLatitude(),newLocation.getLongitude());
    }


    public void onRequestPermissionsResult(Activity context,int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSIONS_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateGPS(context);
                }
                else {
                    Toast.makeText(context,"This app requires access to the location provider in order to work properly",Toast.LENGTH_SHORT);
                    finish();
                }
                break;
        }
    }
}
