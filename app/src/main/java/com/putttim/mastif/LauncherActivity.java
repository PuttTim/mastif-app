package com.putttim.mastif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LauncherActivity extends AppCompatActivity {
    Handler handler = new Handler(Looper.getMainLooper());
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        // Checks if the user is null, if it is then send the user to the LoginActivity,
        // which will prompt the user to login with Google authentication
        // otherwise the user will go into the app.
        if (user  == null) {
            this.intent = new Intent(LauncherActivity.this, LoginActivity.class);
        }
        else {
            this.intent = new Intent(LauncherActivity.this, MainActivity.class);
        }

        // TODO Move FirestoreRepository initial connection into this from MainActivity

        // Short timer to display the logo
        handler.postDelayed(() -> {
            startActivity(this.intent);
            finish();
        }, 1000);
    }


}