package com.asafk.thetimeline.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.asafk.thetimeline.Model.User;
import com.asafk.thetimeline.R;
import com.asafk.thetimeline.Utils.FirebaseUtils;
import com.asafk.thetimeline.Utils.LocalDataUtils;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends TimelineFragment {

    public static final String TAG = SignInFragment.class.getSimpleName();

    private TextInputEditText mEmailInput, mPasswordInput;
    private ProgressBar mProgressBar;

    private NavController mNavController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_sign_in, null);
        initViews(layout);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();

        mNavController = Navigation.findNavController(requireActivity(),
                R.id.auth_host_fragment);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            User user = LocalDataUtils.getInstance().readUserData();
            NavDirections action = (user == null || !user.getId().equals(currentUser))?
                    SignInFragmentDirections.actionSignInFragmentToNewUserFragment()
                    : SignInFragmentDirections.actionSignInFragmentToSplashFragment();
            mNavController.navigate(action);
        }
    }

    @Override
    void initViews(@NonNull View layout) {
        mEmailInput = layout.findViewById(R.id.fragment_sign_in_email_input);
        mPasswordInput = layout.findViewById(R.id.fragment_sign_in_password_input);
        mProgressBar = layout.findViewById(R.id.fragment_sign_in_progressbar);

        MaterialButton signInWithEmail = layout.findViewById(R.id.fragment_sign_in_with_email);
        SignInButton signInWithGmail = layout.findViewById(R.id.fragment_sign_in_gmail);
        MaterialButton navigateToRegisterFragment = layout.findViewById(R.id.fragment_sign_in_navigate_to_register);

        signInWithEmail.setOnClickListener(this);
        signInWithGmail.setOnClickListener(this);
        navigateToRegisterFragment.setOnClickListener(this);
    }

    @Override
    void observeData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_sign_in_with_email: {
                TimelineFragment.hideKeyboard(requireActivity());
                mProgressBar.setVisibility(View.VISIBLE);
                final String email = mEmailInput.getText().toString();
                final String password = mPasswordInput.getText().toString();
                if (!isInputValid(email, password)) {
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                FirebaseUtils.signInWithEmailAndPassword(email, password, isSuccessful -> {
                    if (!isSuccessful) {
                        mProgressBar.setVisibility(View.GONE);
                        // TODO: Handle failure
                        return;
                    }
                    proceedWithAuthentication();
                });
                break;
            }

            case R.id.fragment_sign_in_gmail: {
                FirebaseUtils.signInWithGmail(isSuccessful -> {

                });
                break;
            }

            case R.id.fragment_sign_in_navigate_to_register: {
                NavDirections action = SignInFragmentDirections.actionSignInFragmentToRegisterFragment();
                mNavController.navigate(action);
            }
        }
    }

    private void proceedWithAuthentication(){
        FirebaseUtils.isRegisterProcessComplete(new FirebaseUtils.RegisterProcessCompleteListener() {
            @Override
            public void isRegisterComplete(boolean isComplete) {
                NavDirections action = isComplete? SignInFragmentDirections.actionSignInFragmentToSplashFragment()
                        : SignInFragmentDirections.actionSignInFragmentToNewUserFragment();
                mNavController.navigate(action);
            }

            @Override
            public void onDatabaseError(int errorCode) {
                //TODO: Handle database error
            }
        });
    }

    private boolean isInputValid(final String email, final String password) {
        if (email.isEmpty()) {
            mEmailInput.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            mPasswordInput.requestFocus();
            return false;
        }

        return true;
    }
}
