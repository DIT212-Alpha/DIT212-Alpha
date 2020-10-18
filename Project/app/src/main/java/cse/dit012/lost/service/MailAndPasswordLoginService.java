package cse.dit012.lost.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public final class MailAndPasswordLoginService {
    private static final String TAG = "MailPassLoginService";

    public interface BooleanLoginCallBack {
        void onSuccess(Boolean success);
    }

    private final FirebaseAuth mailAndPasswordLogIn = FirebaseAuth.getInstance();

    /**
     * Tries to sign in the user with the already gained mail and password.
     *
     * @param mail                 e-mail of the user
     * @param password             password of the user
     * @param booleanLoginCallBack Callback
     */
    public void userSignIn(String mail, String password, BooleanLoginCallBack booleanLoginCallBack) {

        mailAndPasswordLogIn.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    booleanLoginCallBack.onSuccess(true);
                    Log.d(TAG, "signInWithEmail: Success");
                } else {
                    booleanLoginCallBack.onSuccess(false);
                    Log.w(TAG, "signInWithEmailAndPassword: failure", task.getException());
                }
            }
        });

    }
}
