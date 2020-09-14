package cse.dit012.lost;

import android.location.Location;

public class Broadcast {
    private Course course;
    private String description;
    private Location location;

    public Broadcast(Course course, String description, Location location) {
        this.course = course;
        this.description = description;
        this.location = location;
    }

    public Course getCourse() {
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
