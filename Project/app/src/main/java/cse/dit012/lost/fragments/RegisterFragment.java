package cse.dit012.lost.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.core.utilities.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import cse.dit012.lost.R;

import cse.dit012.lost.databinding.FragmentRegisterBinding;
import cse.dit012.lost.model.user.User;


public class RegisterFragment extends Fragment {

EditText userName;
EditText surName;
EditText userEmail;
EditText userPassword;

Button registerButton;

ProgressBar progressBar;
NavController navController;

    /**
     * Fragment for register an account, used for registering with mail and password
     * this class will be refactored and some functionality will be added
     */

private FirebaseAuth registerAuthentication;
FragmentRegisterBinding fragmentRegisterBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater,container,false);
        return fragmentRegisterBinding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar =  view.findViewById(R.id.progressBar);
        registerAuthentication = FirebaseAuth.getInstance();

        userName = fragmentRegisterBinding.registerTextUserName ;
        surName = fragmentRegisterBinding.registerTextSurName;
        userEmail = fragmentRegisterBinding.registerTextEmail;
        userPassword = fragmentRegisterBinding.registerTextPassword;
        registerButton = fragmentRegisterBinding.cirRegisterButton ;

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                navController = Navigation.findNavController(view);

                if (validate(email,password)){

                    registerAuthentication.createUserWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()){

                                try {
                                    Toast.makeText(getContext(),"Registration unSucsessful!", Toast.LENGTH_LONG).show();
                                    throw task.getException();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }else {
                                Toast.makeText(getContext(),"Registration Sucsessful! Welcome" , Toast.LENGTH_LONG).show();
                                navController.navigate(R.id.action_registerFragment_to_mapScreenFragment);

                            }

                        }
                    });
                }

            }
        });


    }

    private boolean validate(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        // Reset errors.
        userName.setError(null);
        userPassword.setError(null);

        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Email is required");
            return false;
        } else if (!isEmailValid(email)) {
            userEmail.setError("Enter a valid email");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            userPassword.setError("Password is required");
            return false;
        } else if (!isPasswordValid(password)) {
            userPassword.setError("Password must contain at least 6 characters");
            return false;
        }

        return true;
    }

    public static boolean isEmailValid(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Check password with minimum requirement here(it should be minimum 6 characters)
    public static boolean isPasswordValid(String password){
        return password.length() >= 7;
    }

  
}