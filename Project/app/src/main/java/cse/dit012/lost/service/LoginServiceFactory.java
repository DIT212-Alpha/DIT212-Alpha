package cse.dit012.lost.service;

import androidx.activity.result.ActivityResult;

public final class LoginServiceFactory {
    private LoginServiceFactory() {
    }

    public static LoginService createEmailAndPasswordService(String email, String password) {
        return new EmailAndPasswordLoginService(email, password);
    }

    public static LoginService createGoogleService(ActivityResult result) {
        return new GoogleLoginService(result);
    }
}
