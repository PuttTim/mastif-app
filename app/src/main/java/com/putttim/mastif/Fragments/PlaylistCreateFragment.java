package com.putttim.mastif.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.putttim.mastif.Adapters.LibraryRecyclerAdapter;
import com.putttim.mastif.Adapters.PlaylistCreateRecyclerAdapter;
import com.putttim.mastif.Adapters.PlaylistRecyclerAdapter;
import com.putttim.mastif.Fragments.PlaylistFragmentDirections;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.FragmentPlaylistCreateBinding;

import java.util.ArrayList;
import java.util.List;

public class PlaylistCreateFragment extends Fragment {
    private FragmentPlaylistCreateBinding B;
    private PlaylistCreateRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedViewModel sharedVM;
    private Playlist newPlaylist;
    private String playlistName;
    private String playlistDescription;
    private List<Song> newPlaylistSongs = new ArrayList<>();

    private final TextWatcher onNameChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            playlistName = s.toString();
        }
    };

    private final TextWatcher onDescChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            playlistDescription = s.toString();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlaylistCreateBinding.inflate(inflater, container, false);

        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        B.selectSongRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        // Calls RecyclerAdapter whilst providing the library songs list from Firestore
        mAdapter = new PlaylistCreateRecyclerAdapter(sharedVM.getSongs().getValue(), callback);
        // Sets the RecyclerView adapter and layout inside the fragment's xml
        B.selectSongRecyclerView.setLayoutManager(mLayoutManager);
        B.selectSongRecyclerView.setAdapter(mAdapter);

        // onTextChangedListeners for the EditText Views for setting Playlist name and Playlist description
        B.enterPlaylistName.addTextChangedListener(onNameChange);
        B.enterPlaylistDescription.addTextChangedListener(onDescChange);

        // The FAB confirm onClickListener to commit the new playlist to Firestore (as it calls sharedVM,
        // which will call on FirestoreRepository as
        B.fabConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPlaylist = new Playlist("", playlistName, playlistDescription, "", newPlaylistSongs);
                sharedVM.createPlaylist(newPlaylist);
                Navigation.findNavController(requireView()).navigate(PlaylistCreateFragmentDirections.actionPlaylistCreateFragmentToHomeFragment());
                Log.d("LogD PCFR", "ITS COMING HOME");
            }
        });
        return B.getRoot();
    }

    private final PlaylistCreateRecyclerAdapter.songSelectCallback callback = new PlaylistCreateRecyclerAdapter.songSelectCallback() {
        @Override
        public void onSelectSong(Song selectedSong) {
            newPlaylistSongs.add(selectedSong);
        }
    };
}