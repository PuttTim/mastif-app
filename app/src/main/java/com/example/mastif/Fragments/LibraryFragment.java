package com.example.mastif.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mastif.Objects.Song;
import com.example.mastif.Adapters.LibraryRecyclerAdapter;
import com.example.mastif.ViewModels.PlayerViewModel;
import com.example.mastif.ViewModels.SharedViewModel;
import com.example.mastif.databinding.FragmentLibraryBinding;

import java.util.List;
import java.util.Objects;


public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding B;
    private LibraryRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedViewModel sharedVM;
    private PlayerViewModel playerVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentLibraryBinding.inflate(inflater, container, false);
        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        List<Song> songs = sharedVM.getSongs().getValue();

        B.libraryRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        // Calls LibraryRecyclerAdapter whilst providing the songs list of Song.
        mAdapter = new LibraryRecyclerAdapter(songs, callback);

        B.libraryRecyclerView.setLayoutManager(mLayoutManager);
        B.libraryRecyclerView.setAdapter(mAdapter);

        return B.getRoot();
    }

    private final LibraryRecyclerAdapter.Callback callback = new LibraryRecyclerAdapter.Callback(){
        @Override
        public void onSongClick(List<Song> songList, int position) {

            playerVM.selectSong(Objects.requireNonNull(sharedVM.getSongs().getValue()).get(position));
            playerVM.setPlaylist(songList);

            // This will find the navigation controller of the current view (PlayerFragment)
            // and will navigate to the given directions set in navigation_graph.xml for LibraryFragments
            Navigation.findNavController(requireView()).navigate(LibraryFragmentDirections.actionLibraryFragmentToPlayerFragment());
        }
    };
}
