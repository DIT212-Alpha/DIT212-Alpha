package cse.dit012.lost.android.ui.screen.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import cse.dit012.lost.R;
import cse.dit012.lost.databinding.FragmentLoginBinding;
import cse.dit012.lost.service.AuthenticatedUserService;
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
    NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build();
    GoogleLoginService googleLoginService;
    MailAndPasswordLoginService mailAndPasswordLoginService = new MailAndPasswordLoginService();


    private final ActivityResultLauncher<Intent> request =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                googleLoginService.permession(result);
            });


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
        progressBar = fragmentLoginBinding.progressBar;

        editTextEmail = fragmentLoginBinding.editTextEmail;
        editTextPassword = fragmentLoginBinding.editTextPassword;
        googleLoginService = new GoogleLoginService(getContext());

        imageButtonGoogleSignIn = fragmentLoginBinding.googleSignIn;
        textViewNewUser = view.findViewById(R.id.clickable_text_new_user);
        loginButton = view.findViewById(R.id.cirLoginButton);


        checkIfUSerIsAlreadySignedIn();

        setUpButtons();
    }

    /**
     * Checks if the email and password fields are not empty
     *
     * @param email    - mail of the user
     * @param password - password of the user
     * @return {boolean}
     */

    private boolean validate(String email, String password) {

        editTextEmail.setError(null);
        editTextPassword.setError(null);

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required");
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }

        return true;
    }

    /**
     * check if the user is not null and proceed to the map
     */

    private void checkIfUSerIsAlreadySignedIn() {
        if (AuthenticatedUserService.userService.isLoggedIn()) {
            navController.navigate(R.id.action_loginFragment_to_mapScreenFragment, null, navOptions);
            Toast.makeText(getContext(), "Welcome back", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Set up the buttons with listeners
     */
    private void setUpButtons() {
        imageButtonGoogleSignIn.setOnClickListener(this::googleProceedToMapFragment);
        textViewNewUser.setOnClickListener(this::proceedToRegisterFragment);
        loginButton.setOnClickListener(this::mailLoginUser);
    }

    /**
     * Sign in with google and proceed to mapFragment
     *
     * @param view
     */
    private void googleProceedToMapFragment(View view) {

        googleLoginService.signIn(request, success -> {

            navController.navigate(R.id.action_loginFragment_to_mapScreenFragment, null, navOptions);

        });
    }

    /**
     * proceed to registerFragment
     *
     * @param view
     */
    private void proceedToRegisterFragment(View view) {
        navController.navigate(R.id.action_loginFragment_to_registerFragment);
    }

    /**
     * user mail login, and proceed to mapFragment
     *
     * @param view
     */
    private void mailLoginUser(View view) {

        progressBar.setVisibility(View.VISIBLE);
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (validate(email, password)) {

            mailAndPasswordLoginService.userSignIn(email, password, success -> {

                if (success) {

                    navController.navigate(R.id.action_loginFragment_to_mapScreenFragment, null, navOptions);
                    Toast.makeText(getContext(), "Authentication Success", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Check Your Registration", Toast.LENGTH_LONG).show();
                }

            });
        }
    }

}