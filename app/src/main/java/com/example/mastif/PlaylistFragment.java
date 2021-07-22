package com.example.mastif;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mastif.databinding.FragmentPlayerBinding;


public class PlaylistFragment extends Fragment {
    private FragmentPlayerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlayerBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}