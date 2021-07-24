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
import com.example.mastif.ViewModels.PlayerViewModel;
import com.example.mastif.ViewModels.QueueViewModel;
import com.example.mastif.ViewModels.SharedViewModel;
import com.example.mastif.databinding.FragmentLibraryBinding;

import java.util.List;


public class LibraryFragment extends Fragment implements RecyclerClick {


    private FragmentLibraryBinding B;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedViewModel sharedVM;
    private QueueViewModel queueVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentLibraryBinding.inflate(inflater, container, false);
        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        queueVM = new ViewModelProvider(requireActivity()).get(QueueViewModel.class);

        sharedVM.getSongs().observe(getViewLifecycleOwner(), this::logD);

        List<Song> songs = sharedVM.getSongs().getValue();

        B.libraryRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        // Calls LibraryRecyclerAdapter wihlst providing the songs list of Song.
        mAdapter = new LibraryRecyclerAdapter(songs);

        B.libraryRecyclerView.setLayoutManager(mLayoutManager);
        B.libraryRecyclerView.setAdapter(mAdapter);

        return B.getRoot();
    }

    private void logD(List<Song> list) {
        Log.d("anything", list.toString());
    }


    @Override
    public void onSongClick(int position) {
        queueVM.queueAddSong(sharedVM.getSongs().getValue().get(position));
        Log.d("MASTIF", String.format("Song added %s", sharedVM.getSongs().getValue().get(position)));
    }
}