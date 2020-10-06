package cse.dit012.lost.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cse.dit012.lost.model.user.User;

public class UserInfoService {
    private static UserInfoService userInfo= new UserInfoService();
    private User user;


    public String getName(){
        String name = user.getName();
        return name;
    }

    public String getID(){
        String id = user.getId();
        return id;
    }
    public void createUser(String id,String name){
        user = new User(id,name);
    }
    public static UserInfoService getUserInfoService(){
        return userInfo;
    }
}
