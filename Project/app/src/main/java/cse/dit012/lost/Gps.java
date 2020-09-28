package cse.dit012.lost;

import android.Manifest;
import android.annotation.SuppressLint;
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
    //Communicates with the android system to manage location
    private LocationManager locationManager;
    //Singelton object
    private static Gps gps = new Gps();

    /**
     * Postcondition: Fragment-Context which can not be null (use requireContext(), not getContext(),
     * Must check permission wherever method is called, as "@SuppressLint("MissingPermission")" skips permission check.
     * @param context provided by a "Fragment" from the "requireContext()" method
     *                needed by the LocationManager to communicate with android
     * @return returns the latest location, might be null if no location yet have been found
     */
    @SuppressLint("MissingPermission")
    public LatLng getLocation(Context context){
        this.locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
         Location temp = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
         if(temp != null){
             location = new LatLng(temp.getLatitude(),temp.getLongitude());
         }
        return location;
    }

    //Static method that returns Singelton-object
    public static Gps getGps(){
        return gps;
    }
}
