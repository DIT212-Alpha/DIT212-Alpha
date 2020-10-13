package cse.dit012.lost.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cse.dit012.lost.model.user.User;

/**
 * Author: Mathias
 * Responsibility: Retrieve information from firebase about the user who is logged in on the device
 * Used by: BroadcastInfoWindowFragment
 */
public class UserInfoService {
    private static UserInfoService userInfo= new UserInfoService();

    /**
     * @return Email of current user
     */
    public String getEmailName(){
        String name = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        return name;
    }

    /**
     * @return User id from firebase
     */
    public String getID(){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return id;
    }

    /**
     * @return Singelton object
     */
    public static UserInfoService getUserInfoService(){
        return userInfo;
    }
}
