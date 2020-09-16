package cse.dit012.lost;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Location location;
   FusedLocationProviderClient fusedLocationProviderClient;
    private boolean locationPermissionGranted;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private FirebaseAnalytics analytics;
    boolean hasClicked = false;

    Database dataBase;
    Broadcast broadcastt;
    Button broadcast;
    //DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        analytics = FirebaseAnalytics.getInstance(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        broadcast =  findViewById(R.id.broadcast_btn);

    }


    @Override
    protected void onStart() {
        super.onStart();

        broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("PRESSED PRESSED PRESSED");
                if (locationPermissionGranted){
                    dataBase = new Database(location.getLatitude(),location.getLongitude());
                dataBase.databaseLat.setValue(location.getLatitude()+"");

                dataBase.databaseLon.setValue((location.getLongitude()+""));
                    hasClicked = true;
                }

                if ( hasClicked){
                    System.out.println("Has Clicked");
                    LatLng current = new LatLng(location.getLatitude(),location.getLongitude());
                    broadcastt = new Broadcast(new Course("Dit257"),"Need someone to study with",location.getLatitude(),location.getLongitude());
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));
                    marker.setSnippet(broadcastt.getDescription());
                    marker.showInfoWindow();
                }


            }

        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        getPermession();

        Toast toast = Toast.makeText(getApplicationContext(),
                "Map Is Ready!",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,400);
        toast.show();

        updateLocation();

        getCurrentLocation();

    }

    private void getCurrentLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            location = task.getResult();
                            if (location != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(location.getLatitude(),
                                                location.getLongitude()), 15));
                                LatLng current = new LatLng(location.getLatitude(),location.getLongitude());

                               // mMap.addMarker(new MarkerOptions().position(current));


                            }
                        } else {
                            Log.d("Name", "Current location is null. ");
                            Log.e("Name", "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, 25));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getPermession(){

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

    }



    private void updateLocation() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                location = null;
                getPermession();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


}