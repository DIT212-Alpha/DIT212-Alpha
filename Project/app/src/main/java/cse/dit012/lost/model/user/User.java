package cse.dit012.lost.model.user;

import java.util.ArrayList;
import java.util.List;

import cse.dit012.lost.model.BroadcastObject;

/**
 * Author: Mathias, Responsibility: Holding and managing User information,
 * Used by : Broadcast
 */
public class User {
    private String name;
    private List<BroadcastObject> broadcastObjects;

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

    public void addBroadcastObject(BroadcastObject course){
        broadcastObjects.add(course);
    }

    public void deleteBroadcastObject(int i){
        broadcastObjects.remove(i);
    }

    public BroadcastObject getObject(int i){
        if(broadcastObjects.size()-1 >= i){
            return broadcastObjects.get(i);
        }
        return null;
    }
}
