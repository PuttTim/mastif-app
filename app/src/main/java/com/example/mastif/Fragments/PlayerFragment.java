package com.example.mastif.Fragments;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mastif.Objects.Song;
import com.example.mastif.R;
import com.example.mastif.ViewModels.PlayerViewModel;
import com.example.mastif.ViewModels.QueueViewModel;
import com.example.mastif.ViewModels.SharedViewModel;
import com.example.mastif.databinding.FragmentPlayerBinding;
import com.example.mastif.databinding.SongCardBinding;

import java.util.List;


public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding B;
    private PlayerViewModel playerVM;
    private QueueViewModel queueVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlayerBinding.inflate(inflater, container, false);

        playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        queueVM = new ViewModelProvider(requireActivity()).get(QueueViewModel.class);

        queueVM.getQueue().observe(getViewLifecycleOwner(), this::addToPlaying);
        queueVM.getQueue().observe(getViewLifecycleOwner(), this::startPlaying);
        queueVM.getQueue().observe(getViewLifecycleOwner(), this::weeeeInflate);
//        mPlayerVM.pauseSong();

        System.out.println("HI");

        B.btnPause.setOnClickListener(v -> playerVM.pauseSong());
        B.btnPlay.setOnClickListener(v -> playerVM.resumeSong());

        // Inflate the layout for this fragment
        return B.getRoot();
    }

    private void startPlaying(List<Song> songList) {
        playerVM.startPlayer();
    }

    private void logD(MediaPlayer list) {
        Log.d("anything", list.toString());
    }

    private void addToPlaying (List<Song> songList) {
        Song song;

        if (songList != null && !songList.isEmpty()) {
            song = songList.get(songList.size()-1);
            playerVM.addToPlayingList(song);
        }
    }

    private void weeeeInflate(List<Song> songList) {
        FragmentManager fragmentManager;
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, new PlayerFragment()).commit();
    }





}