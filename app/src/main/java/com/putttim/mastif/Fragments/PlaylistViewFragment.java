package com.putttim.mastif.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.putttim.mastif.Adapters.LibraryRecyclerAdapter;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.R;
import com.putttim.mastif.ViewModels.PlayerViewModel;
import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.FragmentPlaylistViewBinding;

import java.util.List;
import java.util.Objects;


public class PlaylistViewFragment extends Fragment {
    private FragmentPlaylistViewBinding B;
    private SharedViewModel sharedVM;
    private PlayerViewModel playerVM;
    private LibraryRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Song> songsList;
    Playlist viewingPlaylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlaylistViewBinding.inflate(inflater, container, false);

        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);


        viewingPlaylist = sharedVM.getViewingPlaylist();

        songsList = viewingPlaylist.getPlaylistSongs();

        B.playlistViewRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        // Calls LibraryRecyclerAdapter whilst providing the songs list of Song.
        mAdapter = new LibraryRecyclerAdapter(songsList, callback);
        // Sets the RecyclerView adapter and layout inside the fragment's xml
        B.playlistViewRecyclerView.setLayoutManager(mLayoutManager);
        B.playlistViewRecyclerView.setAdapter(mAdapter);

        return B.getRoot();
    }

    private final LibraryRecyclerAdapter.Callback callback = new LibraryRecyclerAdapter.Callback(){
        @Override
        public void onSongClick(List<Song> songList, int position) {
            // Calls PlayerVM methods for setting the song and the playlist.
            playerVM.setSong(songsList.get(position));
            playerVM.setPlaylist(songsList);

            // This will find the navigation controller of the current view (LibraryFragment)
            // and will navigate to the given directions set in navigation_graph.xml for LibraryFragments
            Navigation.findNavController(requireView()).navigate(PlaylistViewFragmentDirections.actionPlaylistViewFragmentToPlayerFragment());
        }
    };
}