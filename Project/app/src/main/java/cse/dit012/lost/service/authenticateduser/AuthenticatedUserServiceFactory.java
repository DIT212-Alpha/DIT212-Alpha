package cse.dit012.lost.service.authenticateduser;

/**
 * Factory for constructing an {@link AuthenticatedUserService}.
 * <p>
 * Author: Mathias Drage
 * Uses: {@link AuthenticatedUserService}, {@link FirebaseAuthenticatedUserService}
 * Used by: {@link AuthenticatedUserService}
 */
final class AuthenticatedUserServiceFactory {
    private AuthenticatedUserServiceFactory() {
    }

    /**
     * Gives an instance of the authenticated user service.
     *
     * @return an instance of the {@link AuthenticatedUserService}
     */
    public static AuthenticatedUserService createUserService() {
        return new FirebaseAuthenticatedUserService();
    }
}