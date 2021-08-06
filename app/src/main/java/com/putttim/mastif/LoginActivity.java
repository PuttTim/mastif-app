package com.putttim.mastif;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.User;
import com.putttim.mastif.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirestoreRepository firestoreRepository = new FirestoreRepository();
    private String userId;
    private String name;
    private String profilePicture;
    List<Playlist> playlists;


    private ActivityLoginBinding B;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    // The menu for logging in with Google Authentication.
    private final ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                // This runs when the user clicks login and finishes their login process
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("LogD LA", "Google Authentication Successful " + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    Log.w("LogD LA", "Google Authentication Failed", e);

                }
            }
        );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        B = ActivityLoginBinding.inflate(LayoutInflater.from(this));


        // The GoogleSignInOptions.Builder builds the API configuration for Google Authentication.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Gets the instance of FirebaseAuth (firebase kinda does this automagically so..)
        mAuth = FirebaseAuth.getInstance();

        // onClickListener for the sign in CardView button.
        B.btnSignIn.setOnClickListener(v -> signIn());

        setContentView(B.getRoot());
    }

    // Sign in method to set up the Google Authentication client (menu)
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    // When Google Authentication is successful, this code will send the user data to the FirestoreRepository,
    // in order to add the user to our Firestore database.

    // TODO Add FirestoreRepository code here
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userCreate();
                            // Sign in success
                            Log.d("LogD LA", "signInWithCredential:success");
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // Sign in Failed
                            Log.w("LogD LA", "Login Failed", task.getException());
                        }
                    }
                });
    }

    private void userCreate() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        userId = firebaseUser.getUid();
        name = firebaseUser.getDisplayName();
        playlists = new ArrayList<>();
        profilePicture = Objects.requireNonNull(firebaseUser.getPhotoUrl()).toString();
        User user = new User(userId, name, profilePicture, playlists);
        firestoreRepository.addUser(user);
    }
}