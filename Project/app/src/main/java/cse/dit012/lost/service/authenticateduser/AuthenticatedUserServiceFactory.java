package cse.dit012.lost.service.authenticateduser;

final class AuthenticatedUserServiceFactory {
    private AuthenticatedUserServiceFactory() {
    }

    public static AuthenticatedUserService createUserService() {
        return new FirebaseAuthenticatedUserService();
    }
}
