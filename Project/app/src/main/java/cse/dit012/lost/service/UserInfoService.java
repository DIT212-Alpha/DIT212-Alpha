package cse.dit012.lost.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cse.dit012.lost.model.user.User;

public class UserInfoService {
    private static UserInfoService userInfo= new UserInfoService();

    public String getName(){
        String name = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        return name;
    }

    public String getID(){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return id;
    }
    public static UserInfoService getUserInfoService(){
        return userInfo;
    }
}
