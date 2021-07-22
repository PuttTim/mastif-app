package com.example.mastif;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mastif.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.homeText.setText("asdadadads");

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}