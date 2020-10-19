package cse.dit012.lost.service.login;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java9.util.concurrent.CompletableFuture;

/**
 * Login service utilizing e-mail and password as credentials.
 * <p>
 * Author: Bashar Oumari
 * Uses: {@link LoginService}
 * Used by: {@link LoginServiceFactory}
 */
final class EmailAndPasswordLoginService implements LoginService {
    private static final String TAG = "EmailPassLoginService";

    private final String email;
    private final String password;

    /**
     * Creates a login service that uses the given e-mail and password to perform the login.
     *
     * @param email    the e-mail to attempt a login with
     * @param password the password to to attempt a login with
     */
    public EmailAndPasswordLoginService(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public CompletableFuture<Void> login() {
        CompletableFuture<Void> future = new CompletableFuture<>();

        // Sign in through Firebase
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "signInWithEmailAndPassword: Success");
                future.complete(null);
            } else {
                Log.w(TAG, "signInWithEmailAndPassword: failure", task.getException());
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }
}
