package cse.dit012.lost.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import cse.dit012.lost.R;

public final class GoogleLoginService {

    public interface LoginCallBack {
        void OnLogIn(boolean success);
    }

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient mGoogleSignInClient;
    private final Context googleSignInContext;
    private LoginCallBack LoginCallBack;


    public GoogleLoginService(Context googleSignInContext) {
        this.googleSignInContext = googleSignInContext;
        createGoogleRequest();

    }

    /**
     * Configure google sign in and build
     */
    private void createGoogleRequest() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(googleSignInContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(googleSignInContext, gso);

    }

    /**
     * opens a google sign in window and tries to authenticate with firebase if google sign in
     * succeeded
     *
     * @param activityResult - ActivityResult
     */

    public void permession(ActivityResult activityResult) {

        if (activityResult.getResultCode() == Activity.RESULT_OK) {
            // There are no request code
            Intent data = activityResult.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // if Google sign in was successful, then authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

                LoginCallBack.OnLogIn(false);
                Toast.makeText(googleSignInContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }

        } else {
            Log.d("GoogleLoginService", "Activity result code not ok");
        }
    }

    /**
     * gets the sign in intent and launches it
     *
     * @param activityResultLauncher - Launch intent with ActivityResultLauncher
     * @param loginCallBack          - Callback
     */

    public void signIn(ActivityResultLauncher<Intent> activityResultLauncher, LoginCallBack loginCallBack) {
        LoginCallBack = loginCallBack;
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signInIntent);

    }


    /**
     * Tries to sign in the user with firebase  with the obtained credentials from google
     *
     * @param idToken - the idToken obtained from google
     */

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            LoginCallBack.OnLogIn(true);

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(googleSignInContext, "Welcome " + user.getDisplayName(), Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            LoginCallBack.OnLogIn(false);
                            Toast.makeText(googleSignInContext, "Authentication Failed", Toast.LENGTH_SHORT).show();

                        }
                    }

                });
    }


}
