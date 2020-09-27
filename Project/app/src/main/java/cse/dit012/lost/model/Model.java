package cse.dit012.lost.model;

import androidx.databinding.Observable;

import com.google.android.gms.maps.model.LatLng;

import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.user.User;

public class Model implements Observable {
    private User user;
    private Broadcast broadcast;
    private LatLng location;
    private boolean needUpdatedLocation;
    private OnPropertyChangedCallback callback;

    public Model(LatLng location,User user){
        needUpdatedLocation = false;
        this.location = location;
        this.user = user;
    }

    public Model(User user){
        needUpdatedLocation = false;
        this.user = user;
    }

    public void setLocation(LatLng newLocation){
        location = newLocation;
    }

    public void createBroadcast(){
        //TODO
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        this.callback = callback;
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }

    public void getLocation(){
        //callback.onPropertyChanged();
    }
}
