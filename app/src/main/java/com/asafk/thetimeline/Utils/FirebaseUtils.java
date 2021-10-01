package com.asafk.thetimeline.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.asafk.thetimeline.Conventions.StorageConventions;
import com.asafk.thetimeline.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUtils {

    public static final String TAG = FirebaseUtils.class.getSimpleName();

    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public interface FirebaseAuthenticationListener {
        void onAuthenticationComplete(boolean isSuccessful);
    }

    public interface LoadUserDataListener {
        void onLoadingFinished(User user);
        void onCancelled(int errorCode);
    }

    public interface RegisterProcessCompleteListener {
        void isRegisterComplete(boolean isComplete);
        void onDatabaseError(int errorCode);
    }

    public static void registerWithEmailAndPassword(@NonNull String email, @NonNull String password,
                                                  @NonNull FirebaseAuthenticationListener listener){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               FirebaseUser user = task.getResult().getUser();
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
               FirebaseUser user = task.getResult().getUser();
               listener.onAuthenticationComplete(true);
           } else {
               Log.d(TAG, "signInWithEmailAndPassword: sign in failed with exception: "
                       + task.getException().getMessage());
               listener.onAuthenticationComplete(false);
           }
        });
    }

    public static void loadUserData(@NonNull LoadUserDataListener listener){
        final DatabaseReference users = database.getReference(StorageConventions.Users.SECTION_NAME);
        final String userId = auth.getCurrentUser().getUid();

        users.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                listener.onLoadingFinished(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onCancelled(error.getCode());
            }
        });
    }

    public static void isRegisterProcessComplete(@NonNull RegisterProcessCompleteListener listener){
        final DatabaseReference users = database.getReference(StorageConventions.Users.SECTION_NAME);
        final String userId = auth.getCurrentUser().getUid();

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listener.isRegisterComplete(snapshot.hasChild(userId));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onDatabaseError(error.getCode());
            }
        });
    }

    public static void writeUserToDatabase(@NonNull User user, @NonNull FirebaseAuthenticationListener listener){
        final DatabaseReference users = database.getReference(StorageConventions.Users.SECTION_NAME);
        final String userId = auth.getCurrentUser().getUid();

        users.child(userId).setValue(user).addOnCompleteListener(task -> {
            listener.onAuthenticationComplete(task.isSuccessful());
        });
    }

    public static void signInWithGmail(@NonNull FirebaseAuthenticationListener listener){

    }
}
