package com.putttim.mastif.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.R;
import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.FragmentPlaylistAddSongBinding;
import com.putttim.mastif.databinding.FragmentPlaylistViewBinding;

public class PlaylistAddSongFragment extends Fragment {
    private FragmentPlaylistAddSongBinding B;
    private SharedViewModel sharedVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlaylistAddSongBinding.inflate(inflater, container, false);
        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);



        return B.getRoot();
    }
}