package cse.dit012.lost.service.authenticateduser;

import cse.dit012.lost.android.service.ActiveBroadcastService;
import cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment;
import cse.dit012.lost.android.ui.screen.map.AddBroadcastFragment;
import cse.dit012.lost.android.ui.screen.map.MapScreenFragment;
import cse.dit012.lost.android.ui.screen.welcome.LoginScreenFragment;
import cse.dit012.lost.model.user.UserId;

/**
 * Service for providing information about and managing the currently authenticated user.
 * <p>
 * Author: Mathias Drage
 * Uses: {@link UserId}, {@link AuthenticatedUserServiceFactory}
 * Used by: {@link ActiveBroadcastService}, {@link AddBroadcastFragment}, {@link AuthenticatedUserServiceFactory},
 * {@link BroadcastInfoWindowFragment}, {@link FirebaseAuthenticatedUserService},
 * {@link LoginScreenFragment}, {@link MapScreenFragment}
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
