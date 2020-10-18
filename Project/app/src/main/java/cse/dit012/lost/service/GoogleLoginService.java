package cse.dit012.lost.service;

import android.app.Activity;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java9.util.concurrent.CompletableFuture;

public final class GoogleLoginService implements LoginService {
    public static final String TAG = "GoogleLoginService";

    private final ActivityResult result;

    public GoogleLoginService(ActivityResult result) {
        this.result = result;
    }

    @Override
    public CompletableFuture<Void> login() {
        CompletableFuture<Void> future = new CompletableFuture<>();

        if (result.getResultCode() != Activity.RESULT_OK) {
            Log.e(TAG, "Activity result code not ok");
            future.completeExceptionally(new RuntimeException("Activity result code not ok"));
            return future;
        }

        try {
            // if Google sign in was successful, then authenticate with Firebase
            GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(result.getData())
                    .getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            FirebaseAuth.getInstance()
                    .signInWithCredential(credential)
                    .addOnCompleteListener(completedTask -> {
                        if (completedTask.isSuccessful()) {
                            future.complete(null);
                        } else {
                            future.completeExceptionally(completedTask.getException());
                        }
                    });
        } catch (ApiException e) {
            Log.wtf(TAG, "Unexpected error parsing sign-in result");
        }

        return future;
    }
}
