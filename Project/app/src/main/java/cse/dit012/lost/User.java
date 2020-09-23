package cse.dit012.lost;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<BroadcastObject> broadcastObjects;
    private Location location;
    private Gps gps;

    public User(String name){
        this.name = name;
        broadcastObjects = new ArrayList<>();
    }

    public User(String name,List<BroadcastObject> broadcastObjects){
        this.name = name;
        this.broadcastObjects = broadcastObjects;
    }

    public String getName(){
        return name;
    }

    public void changeName(String newName){
        name = newName;
    }

    public void addCourse(Course course){
        broadcastObjects.add(course);
    }

    public void deleteCourse(int i){
        broadcastObjects.remove(i);
    }

    public BroadcastObject getObject(int i){
        return broadcastObjects.get(i);
    }

}
