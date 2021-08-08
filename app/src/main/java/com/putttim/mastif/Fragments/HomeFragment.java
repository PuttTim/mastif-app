package com.putttim.mastif.Fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.putttim.mastif.LauncherActivity;
import com.putttim.mastif.LoginActivity;
import com.putttim.mastif.MainActivity;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding B;
    private SharedViewModel sharedVM;
    private String username;
    private String userProfilePicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        B = FragmentHomeBinding.inflate(inflater, container, false);
        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(requireActivity(), gso);
        GoogleSignInAccount userAccount = GoogleSignIn.getLastSignedInAccount(requireActivity());

        assert userAccount != null;
        username = userAccount.getDisplayName();
        userProfilePicture = Objects.requireNonNull(userAccount.getPhotoUrl()).toString();

        B.txtName.setText(String.format("Welcome, %s", username));
        Picasso.get().load(userProfilePicture).transform(new CropCircleTransformation()).into(B.imgProfilePicture);

        // Logs out the user in both Google and Firebase and sends the user to LauncherActivity
        // in the event they want to login again.
        B.btnLogout.setOnClickListener(v -> {
            googleSignInClient.signOut();
            Log.d("LogD HomeFragment", "Google logged out successful");
            FirebaseAuth.getInstance().signOut();
            Log.d("LogD HomeFragment", "Firebase logged Out successful");
            startActivity(new Intent(requireActivity(), LauncherActivity.class));

        });

        // Inflate the layout for this fragment
        return B.getRoot();
    }
}