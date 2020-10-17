package cse.dit012.lost.service;

import cse.dit012.lost.model.user.UserId;

/**
 * Service providing information about the currently authenticated user.
 * <p>
 * Author: Mathias Drage
 * Used by: BroadcastInfoWindowFragment
 */
public interface AuthenticatedUserService {
    /**
     * @return Singleton object
     */
    static AuthenticatedUserService get() {
        return new FirebaseAuthenticatedUserService();
    }

    /**
     * Get weather a user is currently logged in or not.
     *
     * @return true if logged in, false if not
     */
    boolean isLoggedIn();

    /**
     * @return current users UID
     */
    UserId getID();

    /**
     * @return current users email
     */
    String getEmail();

    /**
     * Sign out the currently logged in user.
     */
    void signOutUser();
}
