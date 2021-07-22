package com.example.mastif.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mastif.Objects.Song;
import com.example.mastif.R;
import com.example.mastif.Adapters.LibraryRecyclerAdapter;
import com.example.mastif.RecyclerClick;
import com.example.mastif.ViewModels.SharedViewModel;
import com.example.mastif.databinding.FragmentLibraryBinding;

import java.util.List;


public class LibraryFragment extends Fragment implements RecyclerClick {


    private FragmentLibraryBinding binding;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getSongs().observe(getViewLifecycleOwner(), this::logD);

        List<Song> songs = sharedViewModel.getSongs().getValue();

        binding.libraryRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new LibraryRecyclerAdapter(songs);

        binding.libraryRecyclerView.setLayoutManager(mLayoutManager);
        binding.libraryRecyclerView.setAdapter(mAdapter);

        return binding.getRoot();
    }

    private void logD(List<Song> list) {
        Log.d("anything", list.toString());
    }

    @Override
    public void onSongClick() {
        Fragment home = new HomeFragment();
        FragmentTransaction fragtra = getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, home);
        fragtra.commit();
    }
}