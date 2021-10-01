package com.asafk.thetimeline.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.asafk.thetimeline.R;
import com.asafk.thetimeline.Utils.LocalDataUtils;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        LocalDataUtils.getInstance().initialize(getApplicationContext());
    }
}