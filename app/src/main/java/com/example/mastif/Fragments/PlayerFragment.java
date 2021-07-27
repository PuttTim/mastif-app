package com.example.mastif.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mastif.R;
import com.example.mastif.ViewModels.PlayerViewModel;
import com.example.mastif.databinding.FragmentPlayerBinding;


public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding B;
    private PlayerViewModel playerVM;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlayerBinding.inflate(inflater, container, false);

        playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        // Each line is a different button onClickListener which calls the method inside PlayerViewModel
        B.btnPlayPause.setOnClickListener(v -> togglePlayPauseState());


        // Inflate the layout for this fragment
        return B.getRoot();
    }

    private void togglePlayPauseState() {
        B.btnPlayPause.setImageResource(playerVM.getMp().isPlaying() ? R.drawable.ic_play_button_circle : R.drawable.ic_pause_button_circle);
        playerVM.togglePlayPause();
    }
}