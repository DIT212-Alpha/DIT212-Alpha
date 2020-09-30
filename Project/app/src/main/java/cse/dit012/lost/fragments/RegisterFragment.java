package cse.dit012.lost.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.utilities.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cse.dit012.lost.R;

import cse.dit012.lost.model.user.User;


public class RegisterFragment extends Fragment {

EditText userName;
EditText surName;
EditText userEmail;
EditText userPassword;

Button registerButton;

ProgressBar progressBar;

User user;

private FirebaseAuth registerAuthentication;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar =  view.findViewById(R.id.progressBar);
        registerAuthentication = FirebaseAuth.getInstance();

        userName = view.findViewById(R.id.registerTextUserName);
        surName = view.findViewById(R.id.registerTextSurName);
        userEmail = view.findViewById(R.id.registerTextEmail);
        userPassword = view.findViewById(R.id.registerTextPassword);
        registerButton = view.findViewById(R.id.cirRegisterButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                if (validate(email,password)){

                    registerAuthentication.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(getContext(),"Registration Sucsessful!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
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