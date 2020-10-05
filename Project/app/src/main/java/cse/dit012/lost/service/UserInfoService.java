package cse.dit012.lost.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cse.dit012.lost.model.user.User;

public class UserInfoService {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private String getName(){
        String name = user.getEmail();
        return name;
    }
    private String getSurname(){
        //TODO??
        return null;
    }
    private String getID(){
        String id = user.getUid();
        return id;
    }
    public User createUser(){
        return new User(getID(),getName());
    }
}
