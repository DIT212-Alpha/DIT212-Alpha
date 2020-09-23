package cse.dit012.lost;

import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class Broadcast {
    private BroadcastObject course;
    private String description;
    private double longitude;
    private double latitude;

    public Broadcast(BroadcastObject course, String description, double latitude, double longitude) {
        this.course = course;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public BroadcastObject getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}