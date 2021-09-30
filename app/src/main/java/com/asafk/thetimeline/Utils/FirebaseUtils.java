package com.asafk.thetimeline.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseUtils {

    public static final String TAG = FirebaseUtils.class.getSimpleName();

    private static final FirebaseAuth auth = FirebaseAuth.getInstance();

    public interface FirebaseAuthenticationListener {
        void onAuthenticationComplete(boolean isSuccessful);
    }

    public static void registerWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                  @NonNull FirebaseAuthenticationListener listener){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
                listener.onAuthenticationComplete(true);
           } else {
               Log.d(TAG, "signInWithEmailAndPassword: Register failed with exception: "
                       + task.getException().getMessage());
               listener.onAuthenticationComplete(false);
           }
        });
    }

    public static void signInWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                  @NonNull FirebaseAuthenticationListener listener){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               listener.onAuthenticationComplete(true);
           } else {
               Log.d(TAG, "signInWithEmailAndPassword: sign in failed with exception: "
                       + task.getException().getMessage());
               listener.onAuthenticationComplete(false);
           }
        });
    }

    public static void signInWithGmail(@NonNull FirebaseAuthenticationListener listener){

    }
}
