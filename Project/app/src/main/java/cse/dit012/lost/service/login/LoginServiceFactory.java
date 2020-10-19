package cse.dit012.lost.service.login;

import androidx.activity.result.ActivityResult;

import cse.dit012.lost.android.ui.screen.welcome.LoginScreenFragment;

/**
 * Factory for constructing a {@link LoginService}.
 * <p>
 * Author: Benjamin Sannholm
 * Uses: {@link LoginService}, {@link EmailAndPasswordLoginService}, {@link GoogleLoginService}
 * Used by: {@link LoginScreenFragment}
 */
public final class LoginServiceFactory {
    private LoginServiceFactory() {
    }

    /**
     * Creates a login service that attempts to login to the application using
     * the given e-mail and password.
     *
     * @param email    the e-mail to attempt a login with
     * @param password the password to to attempt a login with
     * @return the created {@link LoginService}
     */
    public static LoginService createEmailAndPasswordService(String email, String password) {
        return new EmailAndPasswordLoginService(email, password);
    }

    /**
     * Creates a login service that attempts to login to the application using
     * the result from a Google sign-in.
     *
     * @param result the result of a Google sign-in
     * @return the created {@link LoginService}
     */
    public static LoginService createGoogleService(ActivityResult result) {
        return new GoogleLoginService(result);
    }
}
