package cse.dit012.lost;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
import androidx.annotation.NonNull;

public class Database extends FragmentActivity {

    private double Longitude;
    private double Latitude;



    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseLat = reference.child("Lat");
    DatabaseReference databaseLon = reference.child("Lon");




    public Database(double Longitude, double Latidude){
        this.Longitude = Longitude;
        this.Latitude = Latidude;
    }

    private void getDataOnChange(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Latitude = Double.parseDouble(snapshot.child("Lat").getValue(String.class));
                Longitude = Double.parseDouble(snapshot.child("Lon").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error", String.valueOf(error));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataOnChange();


    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }


}
