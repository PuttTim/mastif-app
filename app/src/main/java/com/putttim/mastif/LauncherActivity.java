package com.putttim.mastif;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.putttim.mastif.ViewModels.SharedViewModel;

public class LauncherActivity extends AppCompatActivity {
    Handler handler = new Handler(Looper.getMainLooper());
    private Intent intent;
    private SharedViewModel sharedVM;
    FirebaseUser user;
    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();

        // This sets up the initial values of the sharedVM songsList and playlistList
        // whilst the user is loading the screen, as the launcher gets killed too fast,
        // we have a duplicate of this inside MainActivity to finish out the rest of the loading.
        if (user != null) {
            sharedVM = new ViewModelProvider(this).get(SharedViewModel.class);
            sharedVM.startupValueSet();
            sharedVM.setUser(user.getUid(), user.getDisplayName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets the user
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Checks if the user is null, if it is then send the user to the LoginActivity,
        // which will prompt the user to login with Google authentication
        // otherwise the user will go into the app.
        if (user  == null) {
            this.intent = new Intent(LauncherActivity.this, LoginActivity.class);
        }
        else {
            this.intent = new Intent(LauncherActivity.this, MainActivity.class);
        }

        // Short timer to display the logo
        handler.postDelayed(() -> {
            startActivity(this.intent);
            finish();
        }, 1000);
    }


}