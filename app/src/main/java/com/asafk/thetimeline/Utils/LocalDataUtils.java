package com.asafk.thetimeline.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.asafk.thetimeline.Conventions.StorageConventions;
import com.asafk.thetimeline.Model.User;
import com.google.gson.Gson;

public class LocalDataUtils {
    public static final String TAG = LocalDataUtils.class.getSimpleName();

    private static LocalDataUtils instance;

    private Context applicationContext;

    public static LocalDataUtils getInstance(){
        if(instance == null){
            instance = new LocalDataUtils();
        }

        return instance;
    }

    public void initialize(@NonNull Context applicationContext){
        this.applicationContext = applicationContext;
    }

    public void saveUser(User user){
        SharedPreferences userDataPreference =
                applicationContext.getSharedPreferences(StorageConventions.Users.SECTION_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = userDataPreference.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(StorageConventions.Users.SECTION_NAME, json);
        editor.commit();
    }

    public User readUserData(){
        SharedPreferences userDataPreference =
                applicationContext.getSharedPreferences(StorageConventions.Users.SECTION_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String gsonString = userDataPreference.getString(StorageConventions.Users.SECTION_NAME, "");
        User user = gson.fromJson(gsonString, User.class);
        return user;
    }
}
