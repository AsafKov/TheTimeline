package com.asafk.thetimeline.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asafk.thetimeline.R;
import com.asafk.thetimeline.Utils.FirebaseUtils;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignInFragment extends TimelineFragment{

    public static final String TAG = SignInFragment.class.getSimpleName();

    private TextInputEditText mEmailInput, mPasswordInput;

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
    void initViews(@NonNull View layout) {
        MaterialButton signInWithEmail = layout.findViewById(R.id.fragment_sign_in_with_email);
        SignInButton signInWithGmail = layout.findViewById(R.id.fragment_sign_in_gmail);

        signInWithEmail.setOnClickListener(this);
        signInWithGmail.setOnClickListener(this);

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
                FirebaseUtils.signInWithEmailAndPassword(mEmailInput.getText().toString(),
                        mPasswordInput.getText().toString(), isSuccessful -> {

                        });
                break;
            }

            case R.id.fragment_sign_in_gmail: {
                FirebaseUtils.signInWithGmail(isSuccessful -> {

                });
                break;
            }
        }
    }
}
