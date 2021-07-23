package com.example.mastif.Fragments;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mastif.Objects.Song;
import com.example.mastif.R;
import com.example.mastif.ViewModels.PlayerViewModel;
import com.example.mastif.ViewModels.SharedViewModel;
import com.example.mastif.databinding.FragmentPlayerBinding;

import java.util.List;


public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding B;
    private PlayerViewModel mPlayerVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlayerBinding.inflate(inflater, container, false);

        mPlayerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        mPlayerVM.getPlayer().observe(getViewLifecycleOwner(), new Observer<MediaPlayer>() {
            @Override
            public void onChanged(MediaPlayer mediaPlayer) {

            }
        });
//        mPlayerVM.pauseSong();

        Runnable x = () -> System.out.println("HI");

        System.out.println("HI");

        B.btnPause.setOnClickListener((v) -> {

        });

        DialogInterface.OnClickListener c = (dialog, which) -> {

        };

        // Inflate the layout for this fragment
        return B.getRoot();
    }

    private void logD(MediaPlayer list) {
        Log.d("anything", list.toString());
    }

    public void onPlayClick () {
//        B.btnPause.setOnClickListener(v -> mPlayerVM.pauseSong());
    }




}