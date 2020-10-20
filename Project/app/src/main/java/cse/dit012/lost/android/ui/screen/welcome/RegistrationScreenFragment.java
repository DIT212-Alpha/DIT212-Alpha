package cse.dit012.lost.android.ui.screen.welcome;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cse.dit012.lost.R;
import cse.dit012.lost.databinding.FragmentRegisterBinding;

/**
 * User interface for registering an account. Used for registering with e-mail and password.
 * <p>
 * Author: Bashar Oumari
 * Uses: res/layout/fragment_login.xml
 * Used by: res/navigation/nav_graph.xml
 */
public final class RegistrationScreenFragment extends Fragment {
    private static final NavOptions NAV_OPTIONS = new NavOptions.Builder()
            .setPopUpTo(R.id.registerFragment, true)
            .build();

    private FragmentRegisterBinding fragmentRegisterBinding;

    // Navigation controller
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        return fragmentRegisterBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        setupRegisterButton();
    }

    /**
     * Validate the obtained email and password from user
     *
     * @param email    the user's e-mail
     * @param password the user's password
     * @return true if valid, false otherwise
     */
    private boolean validate(String email, String password) {
        // Reset errors
        fragmentRegisterBinding.registerTextUserName.setError(null);
        fragmentRegisterBinding.registerTextPassword.setError(null);

        if (TextUtils.isEmpty(email)) {
            fragmentRegisterBinding.progressBar.setVisibility(View.INVISIBLE);
            fragmentRegisterBinding.registerTextEmail.setError("Email is required");
            return false;
        } else if (!isEmailValid(email)) {
            fragmentRegisterBinding.registerTextEmail.setError("Enter a valid email");
            fragmentRegisterBinding.progressBar.setVisibility(View.INVISIBLE);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            fragmentRegisterBinding.registerTextPassword.setError("Password is required");
            fragmentRegisterBinding.progressBar.setVisibility(View.INVISIBLE);
            return false;
        } else if (!isPasswordValid(password)) {
            fragmentRegisterBinding.registerTextPassword.setError("Password must contain at least 6 characters");
            fragmentRegisterBinding.progressBar.setVisibility(View.INVISIBLE);
            return false;
        }

        return true;
    }

    /**
     * Check if the e-mail is of a good kind
     *
     * @param email the e-mail to check
     * @return true if valid, false otherwise
     */
    private boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Checks the minimum requirement for password (it should be minimum 6 characters)
     *
     * @param password the password to validate
     * @return true if valid, false otherwise
     */
    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    /**
     * Setup the register button
     */
    private void setupRegisterButton() {
        fragmentRegisterBinding.cirRegisterButton.setOnClickListener(view -> registerWithMailAndPassword());
    }

    /**
     * Register the user with the email and password obtained from input fields
     */
    private void registerWithMailAndPassword() {
        fragmentRegisterBinding.progressBar.setVisibility(View.VISIBLE);
        String email = fragmentRegisterBinding.registerTextEmail.getText().toString();
        String password = fragmentRegisterBinding.registerTextPassword.getText().toString();
        String firstname = fragmentRegisterBinding.registerTextUserName.getText().toString();
        String lastname = fragmentRegisterBinding.registerTextSurName.getText().toString();

        if (validate(email, password)) {
            // Create user in Firebase
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w("RegistrationScreen", "Failed to register user", task.getException());
                    fragmentRegisterBinding.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Registration unsuccessful!", Toast.LENGTH_LONG).show();
                    return;
                }

                // Set user display name from first- and lastname
                AuthResult result = task.getResult();
                result.getUser().updateProfile(
                        new UserProfileChangeRequest.Builder()
                                .setDisplayName(firstname + " " + lastname)
                                .build()
                ).addOnSuccessListener(command -> {
                    Toast.makeText(getContext(), "Registration Successful! Welcome", Toast.LENGTH_LONG).show();
                    navController.navigate(R.id.action_registerFragment_to_mapScreenFragment, null, NAV_OPTIONS);
                    fragmentRegisterBinding.progressBar.setVisibility(View.INVISIBLE);
                });
            });
        }
    }
}