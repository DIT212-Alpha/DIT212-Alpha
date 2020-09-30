package cse.dit012.lost.fragments;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import cse.dit012.lost.R;
import cse.dit012.lost.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment  {

    Button loginButton;
    TextView textViewNewUser;

    EditText editTextEmail;
    EditText editTextPassword;
    ImageView imageButtonGoogleSignIn;
    FragmentLoginBinding fragmentLoginBinding;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    NavController navController;

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    private final ActivityResultLauncher<Intent> request =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::permession );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);

        return  fragmentLoginBinding.getRoot();

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createGoogleRequest();
        mAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(view);
        imageButtonGoogleSignIn = fragmentLoginBinding.googleSignIn;

        /**
         * Initialize the navigation controller and change fragment on click
         */


        textViewNewUser = view.findViewById(R.id.clickable_text_new_user);

        loginButton = view.findViewById(R.id.cirLoginButton);
        textViewNewUser.setOnClickListener(view1 -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        imageButtonGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });



    }


    private void createGoogleRequest(){

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(),gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        request.launch(signInIntent);

    }

    private void permession(ActivityResult activityResult){

        if (activityResult.getResultCode() == Activity.RESULT_OK) {
            // There are no request code
            Intent data = activityResult.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();

            }

        }
    }



    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println("Here is the name of user "+ user.getDisplayName());
                            navController.navigate(R.id.action_loginFragment_to_mapScreenFragment);
                            Toast.makeText(getContext(), "Welcome "+user.getDisplayName(), Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


}