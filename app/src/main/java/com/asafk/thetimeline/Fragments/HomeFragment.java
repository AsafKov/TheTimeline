package com.asafk.thetimeline.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asafk.thetimeline.Activities.AuthenticationActivity;
import com.asafk.thetimeline.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends TimelineFragment{

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, null);
        initViews(layout);
        return layout;
    }

    @Override
    void initViews(@NonNull View layout) {
        MaterialButton switchUser = layout.findViewById(R.id.fragment_home_switch_account);
        switchUser.setOnClickListener(this);
    }

    @Override
    void observeData() {

    }

    @Override
    public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(requireContext(), AuthenticationActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}
