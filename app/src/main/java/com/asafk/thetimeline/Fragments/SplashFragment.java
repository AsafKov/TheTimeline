package com.asafk.thetimeline.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.asafk.thetimeline.Activities.MainActivity;
import com.asafk.thetimeline.Model.User;
import com.asafk.thetimeline.R;
import com.asafk.thetimeline.Utils.FirebaseUtils;
import com.asafk.thetimeline.Utils.LocalDataUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashFragment extends TimelineFragment{

    public static final String TAG = SplashFragment.class.getSimpleName();

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_splash, null);
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocalDataUtils localDataUtils = LocalDataUtils.getInstance();
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User localUser = localDataUtils.readUserData();

        if(currentUser.getUid().equals(localUser.getId())){
            new Handler().postDelayed(() -> {
                startActivity(intent);
                requireActivity().finish();
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            FirebaseUtils.loadUserData(new FirebaseUtils.LoadUserDataListener() {
                @Override
                public void onLoadingFinished(User user) {
                    localDataUtils.saveUser(user);
                    startActivity(intent);
                    requireActivity().finish();
                }

                @Override
                public void onCancelled(int errorCode) {
                    // TODO: Handle failure
                }
            });
        }
    }

    @Override
    void initViews(@NonNull View layout) {

    }

    @Override
    void observeData() {

    }

    @Override
    public void onClick(View view) {

    }
}
