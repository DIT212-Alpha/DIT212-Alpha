package cse.dit012.lost.service;

import com.google.firebase.auth.FirebaseAuth;

import cse.dit012.lost.model.user.UserId;

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
    public UserId getID() {
        return new UserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }
}
