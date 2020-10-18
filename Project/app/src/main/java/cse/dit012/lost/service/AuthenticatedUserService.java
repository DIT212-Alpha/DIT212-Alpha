package cse.dit012.lost.service;

import cse.dit012.lost.model.user.UserId;

/**
 * Service providing information about the currently authenticated user.
 * <p>
 * Author: Mathias Drage
 * Used by: BroadcastInfoWindowFragment, LostMapFragment
 */
public interface AuthenticatedUserService {
    /**
     * @return Singleton object
     */
    AuthenticatedUserService userService = AuthenticatedUserServiceFactory.createUserService();

    /**
     * Get weather a user is currently logged in or not.
     *
     * @return true if logged in, false if not
     */
    boolean isLoggedIn();

    /**
     * @return current user's {@link UserId}
     * @throws IllegalStateException if no user is currently logged in
     */
    UserId getID();

    /**
     * @return current user's e-mail
     * @throws IllegalStateException if no user is currently logged in
     */
    String getEmail();

    /**
     * Sign out the currently logged in user.
     */
    void signOutUser();
}
