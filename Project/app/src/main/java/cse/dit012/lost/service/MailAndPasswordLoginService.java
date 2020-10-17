package cse.dit012.lost.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MailAndPasswordLoginService {

    public interface BooleanLoginCallBack {
        void onSuccess(Boolean success);
    }

    private String Tag = "MailAndPasswordLoginService";

    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mailAndPasswordLogIn = FirebaseAuth.getInstance();

    /**
     * Check if the user already signed in and get the current user
     */

    private void checkIfUserIsSignedIn() {

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            }
        };
    }

    /**
     * @param mail                 {String} - mail of the user
     * @param password             {String} - password of the user
     * @param booleanLoginCallBack - Callback
     *                             <p>
     *                             Tries to sign in the user with the already gained mail and password
     */

    public void userSignIn(String mail, String password, BooleanLoginCallBack booleanLoginCallBack) {

        mailAndPasswordLogIn.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    booleanLoginCallBack.onSuccess(true);
                    checkIfUserIsSignedIn();
                    Log.d(Tag, "signInWithEmail: Success");

                } else {
                    booleanLoginCallBack.onSuccess(false);
                    Log.w(Tag, "signInWithEmailAndPassword: failure", task.getException());

                }
            }
        });

    }

    /**
     * Sign out the user
     */

    public void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * get the current user
     *
     * @return - FirebaseUser
     */
    public FirebaseUser getcurrentUser() {
        return mailAndPasswordLogIn.getCurrentUser();
    }


}
