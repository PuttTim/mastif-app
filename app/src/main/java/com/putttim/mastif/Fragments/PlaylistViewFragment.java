package com.putttim.mastif.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.putttim.mastif.Adapters.LibraryRecyclerAdapter;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.ViewModels.PlayerViewModel;
import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.FragmentPlaylistViewBinding;

import java.util.List;


public class PlaylistViewFragment extends Fragment {
    private FragmentPlaylistViewBinding B;
    private SharedViewModel sharedVM;
    private PlayerViewModel playerVM;
    private LibraryRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Song> songsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlaylistViewBinding.inflate(inflater, container, false);
        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        songsList = sharedVM.getViewingPlaylist().getPlaylistSongs();

        // The initialization of RecyclerView for the PlaylistView fragment, passing in the SharedVM's viewingPlaylist
        B.playlistViewRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new LibraryRecyclerAdapter(songsList, callback);
        B.playlistViewRecyclerView.setLayoutManager(mLayoutManager);
        B.playlistViewRecyclerView.setAdapter(mAdapter);

        return B.getRoot();
    }

    private final LibraryRecyclerAdapter.Callback callback = new LibraryRecyclerAdapter.Callback(){
        @Override
        public void onSongClick(List<Song> songList, int position) {
            // Calls PlayerVM methods for setting the song and the playlist.
            playerVM.setCurrentSong(songsList.get(position));
            playerVM.setPlaylist(songsList);

            // This will find the navigation controller of the current view (LibraryFragment)
            // and will navigate to the given directions set in navigation_graph.xml for LibraryFragments
            Navigation.findNavController(requireView()).navigate(PlaylistViewFragmentDirections.actionPlaylistViewFragmentToPlayerFragment());
        }
    };
}