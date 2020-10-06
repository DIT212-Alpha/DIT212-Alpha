package cse.dit012.lost.android.ui.screen.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import cse.dit012.lost.databinding.FragmentLoginBinding;
import cse.dit012.lost.service.UserInfoService;

/**
 * User interface for checking users if they have a Google account to login with
 * just for now, this class contains a check for google for now but this will be refactored after.
 * Author: Bashar Oumari
 */
public final class LoginScreenFragment extends Fragment {
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
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::permession);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return fragmentLoginBinding.getRoot();
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
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInEmail();
            }
        });
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

    private void createGoogleRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        request.launch(signInIntent);
    }

    private void signInEmail() {
        mAuth.signInWithEmailAndPassword("gusdragema@student.gu.se", "1234567")
                .addOnCompleteListener(task -> {
                    task.getResult();
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userId = user.getUid();
                        String userName = user.getEmail();
                        if (userId != null && userName != null) {
                            UserInfoService.getUserInfoService().createUser(user.getUid(), user.getEmail());
                            navController.navigate(R.id.action_loginFragment_to_mapScreenFragment);
                        }
                        else{
                            Toast.makeText(getContext(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                            Toast.makeText(getContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
    }



    private void permession(ActivityResult activityResult) {
        if (activityResult.getResultCode() == Activity.RESULT_OK) {
            // There are no request code
            Intent data = activityResult.getData();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // if Google sign in was successful, then authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        FirebaseUser user = mAuth.getCurrentUser();
                        System.out.println("Here is the name of user " + user.getDisplayName());
                        navController.navigate(R.id.action_loginFragment_to_mapScreenFragment);
                        Toast.makeText(getContext(), "Welcome " + user.getDisplayName(), Toast.LENGTH_LONG).show();
                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(getContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();

                    }

                    // ...
                });
    }
}