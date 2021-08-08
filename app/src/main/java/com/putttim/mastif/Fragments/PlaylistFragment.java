package com.putttim.mastif.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.FragmentPlaylistBinding;

import java.util.ArrayList;
import java.util.List;


public class PlaylistFragment extends Fragment {
    private FragmentPlaylistBinding B;
    private SharedViewModel sharedVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        B = FragmentPlaylistBinding.inflate(inflater, container, false);
        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        List<Song> songList = new ArrayList<>();
        songList.add(sharedVM.getSongs().getValue().get(12));
        songList.add(sharedVM.getSongs().getValue().get(13));
        songList.add(sharedVM.getSongs().getValue().get(14));

        List<Song> songList2 = new ArrayList<>();
        songList2.add(sharedVM.getSongs().getValue().get(0));
        songList2.add(sharedVM.getSongs().getValue().get(1));
        songList2.add(sharedVM.getSongs().getValue().get(2));

        Playlist playlist = new Playlist("", "Playlist 1", "Playlist descriptionahahaha", songList);

        B.btnAddSongToLiked.setOnClickListener(v -> sharedVM.addSongToLiked(sharedVM.getSongs().getValue().get(10)));
        B.btnCreatePlaylist.setOnClickListener(v -> sharedVM.createPlaylist(playlist));
        B.btnAddSongToPlaylist.setOnClickListener(v -> sharedVM.addSongToPlaylist(sharedVM.getSongs().getValue().get(8), "AgSsJGARxK3PEicreFNB"));
        B.btnAddListToPlaylist.setOnClickListener(v -> sharedVM.addListToPlaylist(songList2, "AgSsJGARxK3PEicreFNB"));
        B.btnPrintListSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LogD PF", String.format("print List Size %s", sharedVM.getPlaylistList().getValue().size()));
                Log.d("LogD PF", String.format("print List Playlist %s", sharedVM.getPlaylistList().getValue().get(0).getPlaylistId()));
                if (sharedVM.getPlaylistList().getValue().get(0).getPlaylistSongs().size() > 0) {
                    Log.d("LogD PF", String.format("print List Playlist Song %s", sharedVM.getPlaylistList().getValue().get(0).getPlaylistSongs().get(0).getTitle()));
                    return;
                }
                Log.d("LogD PF", String.format("print List Playlist Song %s", sharedVM.getPlaylistList().getValue().get(0).getPlaylistSongs()));
            }
        });


        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        // Inflate the layout for this fragment
        return B.getRoot();
    }
}