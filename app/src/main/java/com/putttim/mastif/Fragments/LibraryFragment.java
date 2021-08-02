package com.putttim.mastif.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.putttim.mastif.Objects.Song;
import com.putttim.mastif.Adapters.LibraryRecyclerAdapter;
import com.putttim.mastif.ViewModels.PlayerViewModel;
import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.FragmentLibraryBinding;

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

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);


        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        List<Song> songs = sharedVM.getSongs().getValue();

        B.libraryRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        // Calls LibraryRecyclerAdapter whilst providing the songs list of Song.
        mAdapter = new LibraryRecyclerAdapter(songs, callback);
        // Sets the RecyclerView adapter and layout inside the fragment's xml
        B.libraryRecyclerView.setLayoutManager(mLayoutManager);
        B.libraryRecyclerView.setAdapter(mAdapter);

        return B.getRoot();
    }


    // This is the custom interface from the RecyclerAdapter set to call this method with the @Override,
    // when the onSongClick gets called inside the adapter.
    private final LibraryRecyclerAdapter.Callback callback = new LibraryRecyclerAdapter.Callback(){
        @Override
        public void onSongClick(List<Song> songList, int position) {
            // Calls PlayerVM methods for setting the song and the playlist.
            playerVM.setSong(Objects.requireNonNull(sharedVM.getSongs().getValue()).get(position));
            playerVM.setPlaylist(songList);

            // This will find the navigation controller of the current view (LibraryFragment)
            // and will navigate to the given directions set in navigation_graph.xml for LibraryFragments
            Navigation.findNavController(requireView()).navigate(LibraryFragmentDirections.actionLibraryFragmentToPlayerFragment());
        }
    };
}
