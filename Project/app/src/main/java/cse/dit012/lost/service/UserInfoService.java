package cse.dit012.lost.service;

import cse.dit012.lost.model.user.UserId;

/**
 * Author: Mathias Drage
 * Responsibility: Interface for FireBaseUserInfoService
 * Used by: BroadcastInfoWindowFragment
 */
public interface UserInfoService {
    /**
     * @return Singleton object
     */
    static UserInfoService getUserInfoService() {
        return new FirebaseUserInfoService();
    }

    /**
     * @return current users email
     */
    String getEmail();

    /**
     * @return current users UID
     */
    UserId getID();
}
