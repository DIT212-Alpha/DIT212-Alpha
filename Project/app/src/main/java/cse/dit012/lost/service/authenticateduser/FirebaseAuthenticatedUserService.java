package cse.dit012.lost.service.authenticateduser;

import com.google.firebase.auth.FirebaseAuth;

import cse.dit012.lost.model.user.UserId;

import static com.google.common.base.Preconditions.checkState;

/**
 * Retrieves information from Firebase about the user who is logged in on the device.
 * <p>
 * Author: Mathias Drage
 * Uses: {@link UserId}
 * Used by: {@link AuthenticatedUserServiceFactory}
 */
final class FirebaseAuthenticatedUserService implements AuthenticatedUserService {
    @Override
    public boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    @Override
    public String getEmail() {
        checkState(isLoggedIn(), "User is not logged in");
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    @Override
    public UserId getID() {
        checkState(isLoggedIn(), "User is not logged in");
        return new UserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }
}