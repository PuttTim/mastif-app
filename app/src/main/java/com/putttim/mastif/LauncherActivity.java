package com.putttim.mastif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.putttim.mastif.ViewModels.SharedViewModel;

public class LauncherActivity extends AppCompatActivity {
    Handler handler = new Handler(Looper.getMainLooper());
    private Intent intent;
    private SharedViewModel sharedVM;

    @Override
    protected void onStart() {
        super.onStart();
        // This sets up the initial values of the sharedVM songsList and playlistList
        // whilst the user is loading the screen, as the launcher gets killed too fast,
        // we have a duplicate of this inside MainActivity to finish out the rest of the loading.

        sharedVM = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedVM.startupValueSet();
    }

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

        // Short timer to display the logo whilst FirestoreRepo loads into sharedVM
        handler.postDelayed(() -> {
            startActivity(this.intent);
            finish();
        }, 1000);
    }


}