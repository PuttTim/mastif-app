package com.example.mastif.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mastif.Objects.Song;
import com.example.mastif.Adapters.LibraryRecyclerAdapter;
import com.example.mastif.R;
import com.example.mastif.ViewModels.PlayerViewModel;
import com.example.mastif.ViewModels.QueueViewModel;
import com.example.mastif.ViewModels.SharedViewModel;
import com.example.mastif.databinding.FragmentLibraryBinding;

import java.util.List;


public class LibraryFragment extends Fragment {


    private FragmentLibraryBinding B;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedViewModel sharedVM;
    private PlayerViewModel playerVM;

    private QueueViewModel queueVM;
    private final LibraryRecyclerAdapter.Callback callback = new LibraryRecyclerAdapter.Callback(){
        @Override
        public void onSongClick(Song song) {
            queueVM.queueAddSong(song);
            Log.d("queue", String.format("Song added %s", song.getTitle()));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentLibraryBinding.inflate(inflater, container, false);
        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        queueVM = new ViewModelProvider(requireActivity()).get(QueueViewModel.class);
        playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        sharedVM.getSongs().observe(getViewLifecycleOwner(), this::logD);


        queueVM.getQueue().observe(getViewLifecycleOwner(), this::addToPlaying);
//        queueVM.getQueue().observe(getViewLifecycleOwner(), this::startPlaying);


        List<Song> songs = sharedVM.getSongs().getValue();

        B.libraryRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        // Calls LibraryRecyclerAdapter wihlst providing the songs list of Song.
        mAdapter = new LibraryRecyclerAdapter(songs, callback);

        B.libraryRecyclerView.setLayoutManager(mLayoutManager);
        B.libraryRecyclerView.setAdapter(mAdapter);

        return B.getRoot();
    }

    private void logD(List<Song> list) {
        Log.d("anything", list.toString());
    }

    private void startPlaying(List<Song> songList) {
        playerVM.startPlayer();
    }



    private void addToPlaying (List<Song> songList) {
        Song song;

        if (songList != null && !songList.isEmpty()) {
            song = songList.get(songList.size()-1);
            Log.d("LogcatDebug", String.format("Song added %s", song.getTitle()));
            playerVM.addToPlayingList(song);
        }

    }

    private void weeeeInflate() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, new PlayerFragment()).commit();
    }

}