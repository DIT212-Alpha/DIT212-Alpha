package cse.dit012.lost.android.ui.screen.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cse.dit012.lost.R;
import cse.dit012.lost.databinding.FragmentLoginBinding;
import cse.dit012.lost.service.authenticateduser.AuthenticatedUserService;
import cse.dit012.lost.service.login.LoginService;
import cse.dit012.lost.service.login.LoginServiceFactory;
import java9.util.concurrent.CompletableFuture;

/**
 * User interface for logging in the user through various services.
 * <p>
 * Author: Bashar Oumari
 * Uses: res/layout/fragment_login.xml, {@link AuthenticatedUserService}, {@link LoginService}, {@link LoginServiceFactory}
 * Used by: res/navigation/nav_graph.xml
 */
public final class LoginScreenFragment extends Fragment {
    private static final NavOptions NAV_OPTIONS = new NavOptions.Builder()
            .setPopUpTo(R.id.loginFragment, true)
            .build();

    private FragmentLoginBinding fragmentLoginBinding;

    // Navigation controller
    private NavController navController;

    // Launcher for starting the Google login process
    private final ActivityResultLauncher<Intent> googleSignInActivityLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                loginUsingService(LoginServiceFactory.createGoogleService(result));
            });

    /**
     * @return sign in {@link Intent} for Google login
     */
    private Intent getGoogleSignInIntent() {
        Context context = requireContext();
        return GoogleSignIn.getClient(
                context,
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(context.getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build())
                .getSignInIntent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        checkIfUserIsAlreadySignedIn();
        setUpButtons();
    }

    /**
     * Logins in using a given login service and handles the outcome.
     *
     * @param service the {@link LoginService} to use
     * @return a future completing with the result of the login or failing
     */
    private CompletableFuture<Void> loginUsingService(LoginService service) {
        return service.login().thenAccept(unused -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String name = user.getDisplayName() == null ? "" : " " + user.getDisplayName();
            Toast.makeText(requireContext(), "Welcome" + name, Toast.LENGTH_LONG).show();
            navController.navigate(R.id.action_loginFragment_to_mapScreenFragment, null, NAV_OPTIONS);
        }).exceptionally(throwable -> {
            Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
            return null;
        });
    }

    /**
     * Check if the user is logged in and and proceeds to the map if so
     */
    private void checkIfUserIsAlreadySignedIn() {
        if (AuthenticatedUserService.userService.isLoggedIn()) {
            navController.navigate(R.id.action_loginFragment_to_mapScreenFragment, null, NAV_OPTIONS);
            Toast.makeText(getContext(), "Welcome back", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Set up the buttons with listeners
     */
    private void setUpButtons() {
        fragmentLoginBinding.googleSignIn.setOnClickListener(view -> beginGoogleLogin());
        fragmentLoginBinding.clickableTextNewUser.setOnClickListener(view -> proceedToRegisterFragment());
        fragmentLoginBinding.cirLoginButton.setOnClickListener(view -> mailLoginUser());
    }

    /**
     * Starts the process of logging in using Google authentication
     */
    private void beginGoogleLogin() {
        googleSignInActivityLauncher.launch(getGoogleSignInIntent());
    }

    /**
     * Proceeds to registration screen
     */
    private void proceedToRegisterFragment() {
        navController.navigate(R.id.action_loginFragment_to_registerFragment);
    }

    /**
     * Checks if the email and password field values are valid
     *
     * @param email    email of the user
     * @param password password of the user
     * @return true if valid, false otherwise
     */
    private boolean validate(String email, String password) {
        fragmentLoginBinding.editTextEmail.setError(null);
        fragmentLoginBinding.editTextPassword.setError(null);

        if (TextUtils.isEmpty(email)) {
            fragmentLoginBinding.editTextEmail.setError("Email is required");
            fragmentLoginBinding.progressBar.setVisibility(View.INVISIBLE);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            fragmentLoginBinding.editTextPassword.setError("Password is required");
            fragmentLoginBinding.progressBar.setVisibility(View.INVISIBLE);
            return false;
        }

        return true;
    }

    /**
     * Starts the process of logging in using e-mail and password
     */
    private void mailLoginUser() {
        fragmentLoginBinding.progressBar.setVisibility(View.VISIBLE);
        String email = fragmentLoginBinding.editTextEmail.getText().toString();
        String password = fragmentLoginBinding.editTextPassword.getText().toString();

        if (validate(email, password)) {
            loginUsingService(LoginServiceFactory.createEmailAndPasswordService(email, password))
                    .handle((aVoid, throwable) -> {
                        fragmentLoginBinding.progressBar.setVisibility(View.INVISIBLE);
                        return null;
                    });
        }
    }
}