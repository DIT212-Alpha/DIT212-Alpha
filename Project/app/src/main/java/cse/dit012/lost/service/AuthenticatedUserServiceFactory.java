package cse.dit012.lost.service;

public final class AuthenticatedUserServiceFactory {
    private AuthenticatedUserServiceFactory() {
    }

    public static AuthenticatedUserService createUserService() {
        return new FirebaseAuthenticatedUserService();
    }
}
