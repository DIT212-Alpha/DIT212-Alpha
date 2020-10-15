package cse.dit012.lost.android.ui.screen.welcome;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cse.dit012.lost.R;
import cse.dit012.lost.databinding.FragmentRegisterBinding;

/**
 * User interface for register an account, used for registering with mail and password
 * this class will be refactored and some functionality will be added
 * Author: Bashar Oumari
 */
public final class RegistrationScreenFragment extends Fragment {
    EditText userName;
    EditText surName;
    EditText userEmail;
    EditText userPassword;

    Button registerButton;

    ProgressBar progressBar;
    NavController navController;

    private FirebaseAuth registerAuthentication;
    FragmentRegisterBinding fragmentRegisterBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        return fragmentRegisterBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = fragmentRegisterBinding.progressBar;
        registerAuthentication = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(view);

        userName = fragmentRegisterBinding.registerTextUserName;
        surName = fragmentRegisterBinding.registerTextSurName;
        userEmail = fragmentRegisterBinding.registerTextEmail;
        userPassword = fragmentRegisterBinding.registerTextPassword;
        registerButton = fragmentRegisterBinding.cirRegisterButton;


        setupRegisterButton();
    }

    /**
     * Validate the obtained email and password from user
     * @param email - users email
     * @param password - users password
     * @return {boolean}
     */

    private boolean validate(String email, String password) {

        // Reset errors.
        userName.setError(null);
        userPassword.setError(null);

        if (TextUtils.isEmpty(email)) {
            progressBar.setVisibility(View.INVISIBLE);
            userEmail.setError("Email is required");

            return false;
        } else if (!EmailValidate(email)) {
            userEmail.setError("Enter a valid email");
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            userPassword.setError("Password is required");
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        } else if (!PasswordValidate(password)) {
            userPassword.setError("Password must contain at least 6 characters");
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }

        return true;
    }

    /**
     * Check if the email is of a good kind
     * @param email - mail of the user
     * @return - boolean
     */

    public boolean EmailValidate(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Check password with minimum requirement here(it should be minimum 6 characters)

    /**
     * checks the minimum requirement for password
     * @param password - password of the user
     * @return - boolean
     */
    public boolean PasswordValidate(String password) {
        return password.length() >= 7;
    }

    /**
     * Setup the register button
     */

    private void setupRegisterButton(){
        registerButton.setOnClickListener(this :: registerWithMailAndPassword);
    }

    /**
     * register the user with the email and password obtained
     * @param view
     */

    private void registerWithMailAndPassword(View view){

        progressBar.setVisibility(View.VISIBLE);
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (validate(email, password)) {

            registerAuthentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                if (!task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);

                    try {

                        Toast.makeText(getContext(), "Registration unSucsessful!", Toast.LENGTH_LONG).show();
                        throw task.getException();
                    }

                    catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getContext(), "Registration Sucsessful! Welcome", Toast.LENGTH_LONG).show();
                    navController.navigate(R.id.action_registerFragment_to_mapScreenFragment);
                    progressBar.setVisibility(View.INVISIBLE);
                }

            });
        }
    }
}