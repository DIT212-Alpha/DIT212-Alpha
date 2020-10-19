package cse.dit012.lost.service.login;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java9.util.concurrent.CompletableFuture;

/**
 * Login service utilizing e-mail and password as credentials.
 * <p>
 * Author: Bashar Oumari
 */
final class EmailAndPasswordLoginService implements LoginService {
    private static final String TAG = "EmailPassLoginService";

    private final String email;
    private final String password;

    public EmailAndPasswordLoginService(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public CompletableFuture<Void> login() {
        CompletableFuture<Void> future = new CompletableFuture<>();

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
