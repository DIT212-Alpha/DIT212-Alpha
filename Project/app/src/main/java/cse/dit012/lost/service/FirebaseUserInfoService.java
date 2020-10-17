package cse.dit012.lost.service;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Author: Mathias
 * Responsibility: Retrieve information from firebase about the user who is logged in on the device
 * Used by: BroadcastInfoWindowFragment
 */
final class FirebaseUserInfoService implements UserInfoService {

    /**
     * @return Email of current user
     */
    public String getEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    /**
     * @return User id from firebase
     */
    public String getID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
