package cse.dit012.lost.android.ui.screen.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import androidx.activity.result.ActivityResultCallback;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

import cse.dit012.lost.R;
import cse.dit012.lost.databinding.FragmentLoginBinding;
import cse.dit012.lost.service.GoogleLoginService;
import cse.dit012.lost.service.MailAndPasswordLoginService;

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

    NavController navController;
    GoogleLoginService googleLoginService;
    MailAndPasswordLoginService mailAndPasswordLoginService = new MailAndPasswordLoginService();


    private final ActivityResultLauncher<Intent> request =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {googleLoginService.permession(result); } );


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return fragmentLoginBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        googleLoginService = new GoogleLoginService(getContext());

        if (mailAndPasswordLoginService.returnTrueIfSignedIn() && ! mailAndPasswordLoginService.signOutUser()){
            navController.navigate(R.id.action_loginFragment_to_mapScreenFragment);
            Toast.makeText(getContext(), "Welcome back", Toast.LENGTH_LONG).show();
        }

        imageButtonGoogleSignIn = fragmentLoginBinding.googleSignIn;

        /**
         * Initialize the navigation controller and change fragment on click
         */

        textViewNewUser = view.findViewById(R.id.clickable_text_new_user);

        loginButton = view.findViewById(R.id.cirLoginButton);
        textViewNewUser.setOnClickListener(view1 -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
        editTextEmail = fragmentLoginBinding.editTextEmail;
        editTextPassword =fragmentLoginBinding.editTextPassword;


        imageButtonGoogleSignIn.setOnClickListener(view12 -> googleLoginService.signIn(request, success -> {

            navController.navigate(R.id.action_loginFragment_to_mapScreenFragment);
        }));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                mailAndPasswordLoginService.userSignIn(email, password);

                navController.navigate(R.id.action_loginFragment_to_mapScreenFragment);
                Toast.makeText(getContext(), "Authentication Success", Toast.LENGTH_LONG).show();
            }
            });






    }
}