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
    private Button checkIn;



    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionReference = reference.child("LatLong");







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

         checkIn =  findViewById(R.id.button1);

    }


    @Override
    protected void onStart() {
        super.onStart();

        conditionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String location = snapshot.getValue(String.class);
                System.out.println("The Location is :"+location);;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = checkIn.getText().toString();

                switch (s){
                    case "CHECK IN":
                        checkIn.setText(R.string.check_ut);
                        //conditionReference.setValue(location.getLatitude()+","+location.getLongitude());
                        conditionReference.setValue("Sunny");
                        break;

                    case "CHECK OUT":
                        checkIn.setText(R.string.check_in);
                        //conditionReference.setValue((location.getLatitude()+","+location.getLongitude()));
                        conditionReference.setValue("Foggy");
                        break;
                }

                getCurrentLocation();

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