package com.asafk.thetimeline.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseUtils {

    public static final String TAG = FirebaseUtils.class.getSimpleName();

    public interface FirebaseRegisterListener{
        void onRegisterComplete(boolean isSuccessful);
    }

    public static void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                  @NonNull FirebaseRegisterListener listener){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
                listener.onRegisterComplete(true);
           } else {
               Log.e(TAG, "signInWithEmailAndPassword: Register failed with exception: " + task.getException().getMessage());
               listener.onRegisterComplete(false);
           }
        });
    }

    public static void signInWithGmail(@NonNull FirebaseRegisterListener listener){

    }
}
