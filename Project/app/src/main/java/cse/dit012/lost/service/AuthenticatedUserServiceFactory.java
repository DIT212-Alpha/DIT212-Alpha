package cse.dit012.lost.service;

public class AuthenticatedUserServiceFactory {
    public static AuthenticatedUserService createUserService(){
        return new FirebaseAuthenticatedUserService();
    }
}
