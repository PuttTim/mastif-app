package com.putttim.mastif.Fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.putttim.mastif.LauncherActivity;
import com.putttim.mastif.MainActivity;
import com.putttim.mastif.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding B;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        B = FragmentHomeBinding.inflate(inflater, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        B.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Log.d("LogD HomeFragment", "Logged Out Successful");
                startActivity(new Intent(requireActivity(), LauncherActivity.class));
            }
        });

        // Inflate the layout for this fragment
        return B.getRoot();
    }
}