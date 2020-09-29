package cse.dit012.lost.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import cse.dit012.lost.R;



public class LoginFragment extends Fragment  {

    Button loginButton;
    TextView textViewNewUser;

    EditText editTextEmail;
    EditText editTextPassword;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_login,container,false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * Initialize the navigation controller and change fragment on click
         */
        final NavController navController = Navigation.findNavController(view);

        textViewNewUser = view.findViewById(R.id.clickable_text_new_user);

        loginButton = view.findViewById(R.id.cirLoginButton);
        textViewNewUser.setOnClickListener(view1 -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);



    }




}