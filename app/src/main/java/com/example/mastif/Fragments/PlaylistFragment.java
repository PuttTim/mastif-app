package com.example.mastif.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mastif.databinding.FragmentPlaylistBinding;


public class PlaylistFragment extends Fragment {
    private FragmentPlaylistBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}