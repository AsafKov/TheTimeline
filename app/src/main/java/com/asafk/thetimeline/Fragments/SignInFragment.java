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
import com.google.android.gms.common.SignInButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class SignInFragment extends TimelineFragment{

    public static final String TAG = SignInFragment.class.getSimpleName();

    private TextInputEditText mEmailInput, mPasswordInput;

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
    }

    @Override
    void initViews(@NonNull View layout) {
        MaterialButton signInWithEmail = layout.findViewById(R.id.fragment_sign_in_with_email);
        SignInButton signInWithGmail = layout.findViewById(R.id.fragment_sign_in_gmail);
        MaterialTextView navigateToRegisterFragment = layout.findViewById(R.id.fragment_sign_in_navigate_to_register);

        signInWithEmail.setOnClickListener(this);
        signInWithGmail.setOnClickListener(this);
        navigateToRegisterFragment.setOnClickListener(this);

        mEmailInput = layout.findViewById(R.id.fragment_sign_in_email_input);
        mPasswordInput = layout.findViewById(R.id.fragment_sign_in_password_input);
    }

    @Override
    void observeData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_sign_in_with_email: {
                //TODO: remove
                if(mEmailInput.getText().toString().equals("admin")){
                    TimelineFragment.hideKeyboard(requireActivity());
                    NavDirections action = SignInFragmentDirections.actionSignInFragmentToNewUserFragment();
                    mNavController.navigate(action);
                } else {
                    FirebaseUtils.signInWithEmailAndPassword(mEmailInput.getText().toString(),
                            mPasswordInput.getText().toString(), isSuccessful -> {
                                TimelineFragment.hideKeyboard(requireActivity());
                                NavDirections action = SignInFragmentDirections.actionSignInFragmentToNewUserFragment();
                                mNavController.navigate(action);
                            });
                }
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
}
