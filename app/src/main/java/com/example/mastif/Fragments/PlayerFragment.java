package com.example.mastif.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
    private View.OnClickListener onClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        B = FragmentPlayerBinding.inflate(inflater, container, false);

        PlayerViewModel playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        SharedViewModel sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        playerVM.getPlayer().observe(getViewLifecycleOwner(), this::logD);

        playerVM.pauseSong();


        B.btnPause.setOnClickListener(playerVM.pauseSong());

        // Inflate the layout for this fragment
        return B.getRoot();
    }

    private void logD(List<Song> list) {
        Log.d("anything", list.toString());
    }

    public void onPlayClick () {
        B.btnPause.setOnClickListener(playerVM.new);
        playerVM
    }




}