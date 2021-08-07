package com.putttim.mastif.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.FragmentPlaylistBinding;


public class PlaylistFragment extends Fragment {
    private FragmentPlaylistBinding B;
    private SharedViewModel sharedVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        B = FragmentPlaylistBinding.inflate(inflater, container, false);
        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Playlist playlist = new Playlist();

        B.btnSetPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedVM.setPlaylistSongs(playlist);
            }
        });

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        // Inflate the layout for this fragment
        return B.getRoot();
    }
}