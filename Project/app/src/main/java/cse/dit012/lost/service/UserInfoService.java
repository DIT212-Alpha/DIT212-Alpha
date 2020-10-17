package cse.dit012.lost.service;

/**
 * Author: Mathias Drage
 * Responsibility: Interface for FireBaseUserInfoService
 * Used by: BroadcastInfoWindowFragment
 */
public interface UserInfoService {
    /**
     * Singelton object of type FirebaseUserInfoService
     */
    UserInfoService useInfo = new FirebaseUserInfoService();

    /**
     * @return current users email
     */
    String getEmail();

    /**
     * @return current users UID
     */
    String getID();

    /**
     * @return Singelton object
     */
    static UserInfoService getUserInfoService() {
        return useInfo;
    }
}
