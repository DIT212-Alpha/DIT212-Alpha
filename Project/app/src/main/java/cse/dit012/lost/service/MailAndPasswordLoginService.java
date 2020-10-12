package cse.dit012.lost.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import cse.dit012.lost.android.ui.screen.welcome.LoginScreenFragment;

public class MailAndPasswordLoginService {


    private String Tag = "MailAndPasswordLoginService";

    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mailAndPasswordLogIn = FirebaseAuth.getInstance();



    private void checkIfUserIsSignedIn(){

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null){
                    returnTrueIfSignedIn();
                }
                else {
                    returnFalseIfSignedIn();
                }
            }
        };
    }

    public boolean returnTrueIfSignedIn(){
            return true;
    }
    public boolean returnFalseIfSignedIn(){
        return false;
    }

    public void userSignIn(String mail, String password){
        mailAndPasswordLogIn.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    checkIfUserIsSignedIn();
                    Log.d(Tag, "signInWithEmail: Success");
                }
                else {
                    Log.w(Tag, "signInWithEmailAndPassword: failure", task.getException());
                }
            }
        });


    }

    public boolean signOutUser(){
        FirebaseAuth.getInstance().signOut();
        return true;
    }


}
