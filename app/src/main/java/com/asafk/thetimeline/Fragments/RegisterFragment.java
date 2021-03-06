package com.asafk.thetimeline.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.asafk.thetimeline.R;
import com.asafk.thetimeline.Utils.FirebaseUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterFragment extends TimelineFragment {

    public static final String TAG = RegisterFragment.class.getSimpleName();

    private TextInputEditText mEmail, mPassword, mPasswordConfirmation;
    private CircularProgressIndicator mProgressBar;

    private NavController mNavController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_register, null);
        initViews(layout);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();

        mNavController = Navigation.findNavController(requireActivity(), R.id.auth_host_fragment);
    }

    @Override
    void initViews(@NonNull View layout) {
        mEmail = layout.findViewById(R.id.fragment_register_email_input);
        mPassword = layout.findViewById(R.id.fragment_register_password_input);
        mPasswordConfirmation = layout.findViewById(R.id.fragment_register_confirm_password_input);
        mProgressBar = layout.findViewById(R.id.fragment_register_progressbar);

        MaterialButton createUser = layout.findViewById(R.id.fragment_register_create_user);
        MaterialButton navigateToSignIn = layout.findViewById(R.id.fragment_register_navigate_to_sign_in);

        createUser.setOnClickListener(this);
        navigateToSignIn.setOnClickListener(this);
    }

    @Override
    void observeData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_register_create_user: {
                TimelineFragment.hideKeyboard(requireActivity());
                mProgressBar.setVisibility(View.VISIBLE);
                TimelineFragment.hideKeyboard(requireActivity());
                if(inputValidation()){
                    FirebaseUtils.registerWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString(),
                            isSuccessful -> {
                        if(isSuccessful){
                            NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToNewUserFragment();
                            mNavController.navigate(action);
                        } else {
                           //TODO: handle failure
                           mProgressBar.setVisibility(View.GONE);
                        }
                    });
                } else {
                    // TODO: Notify on un-matching passwords
                    mProgressBar.setVisibility(View.GONE);
                }
                break;
            }

            case R.id.fragment_register_navigate_to_sign_in: {
                mNavController.popBackStack();
            }
        }
    }

    private boolean inputValidation(){
        if(mEmail.getText().toString().isEmpty()){
            mEmail.requestFocus();
            return false;
        }

        if(mPassword.getText().toString().isEmpty()){
            mPassword.requestFocus();
            return false;
        }

        return mPassword.getText().toString().equals(mPasswordConfirmation.getText().toString());
    }
}
