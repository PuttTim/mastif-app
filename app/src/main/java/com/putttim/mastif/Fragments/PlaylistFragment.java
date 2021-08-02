package com.putttim.mastif.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.putttim.mastif.databinding.FragmentPlaylistBinding;


public class PlaylistFragment extends Fragment {
    private FragmentPlaylistBinding B;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        B = FragmentPlaylistBinding.inflate(inflater, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        // Inflate the layout for this fragment
        return B.getRoot();
    }
}