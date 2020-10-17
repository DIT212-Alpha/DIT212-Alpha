package cse.dit012.lost.service;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Author: Mathias
 * Responsibility: Retrieve information from firebase about the user who is logged in on the device
 * Used by: BroadcastInfoWindowFragment
 */
public class FirebaseUserInfoService implements UserInfoService {

    /**
     * @return Email of current user
     */
    public String getEmail() {
        String name = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        return name;
    }

    /**
     * @return User id from firebase
     */
    public String getID() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return id;
    }
}
