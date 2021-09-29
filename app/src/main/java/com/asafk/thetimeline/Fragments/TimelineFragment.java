package com.asafk.thetimeline.Fragments;

import android.view.View;

import androidx.annotation.NonNull;

public abstract class TimelineFragment extends androidx.fragment.app.Fragment implements View.OnClickListener{
    abstract void initViews(@NonNull View layout);
    abstract void observeData();
}
