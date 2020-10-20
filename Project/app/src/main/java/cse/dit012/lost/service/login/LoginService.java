package cse.dit012.lost.service.login;

import cse.dit012.lost.android.ui.screen.welcome.LoginScreenFragment;
import java9.util.concurrent.CompletableFuture;

/**
 * A service for logging in a user.
 * <p>
 * Author: Benjamin Sannholm
 * Used by: {@link LoginServiceFactory}, {@link EmailAndPasswordLoginService},
 * {@link GoogleLoginService}, {@link LoginScreenFragment}
 */
public interface LoginService {
    /**
     * Tries to sign in the user.
     *
     * @return a future completing if the login succeeded and completes exceptionally if it failed
     */
    CompletableFuture<Void> login();
}
