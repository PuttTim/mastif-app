package com.putttim.mastif.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.putttim.mastif.Adapters.PlaylistRecyclerAdapter;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.FragmentPlaylistBinding;

import java.util.ArrayList;
import java.util.List;


public class PlaylistFragment extends Fragment {
    private FragmentPlaylistBinding B;
    private PlaylistRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedViewModel sharedVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        B = FragmentPlaylistBinding.inflate(inflater, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);


        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        List<Playlist> playlistList = sharedVM.getPlaylistList().getValue();

        B.playlistRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mAdapter = new PlaylistRecyclerAdapter(playlistList, callback);

        B.playlistRecyclerView.setLayoutManager(mLayoutManager);
        B.playlistRecyclerView.setAdapter(mAdapter);

        return B.getRoot();
    }

    private final PlaylistRecyclerAdapter.Callback callback = new PlaylistRecyclerAdapter.Callback() {
        @Override
        public void onPlaylistClick(Playlist selectedPlaylist) {
            Log.d("LogD PF", String.format("Selected playlist: %s", selectedPlaylist.getPlaylistId()));
        }

    };
}