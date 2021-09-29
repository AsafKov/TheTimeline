package com.asafk.thetimeline.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asafk.thetimeline.R;

class SignInFragment extends TimelineFragment{

    public static final String TAG = SignInFragment.class.getSimpleName();

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

    }

    @Override
    public void onClick(View view) {

    }
}
