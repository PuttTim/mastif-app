package com.putttim.mastif.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.putttim.mastif.Adapters.PlaylistRecyclerAdapter;
import com.putttim.mastif.Objects.Playlist;
import com.putttim.mastif.ViewModels.SharedViewModel;
import com.putttim.mastif.databinding.FragmentPlaylistBinding;


public class PlaylistFragment extends Fragment {
    private FragmentPlaylistBinding B;
    private PlaylistRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedViewModel sharedVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlaylistBinding.inflate(inflater, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        sharedVM = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // The initialization of RecyclerView for the Playlist fragment.
        B.playlistRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new PlaylistRecyclerAdapter(sharedVM.getPlaylistList().getValue(), callback);
        B.playlistRecyclerView.setLayoutManager(mLayoutManager);
        B.playlistRecyclerView.setAdapter(mAdapter);

        // on FAB click create of the playlist, it'll send the user to the playlistCreate fragment.
        B.fabCreatePlaylist.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistCreateFragment()));

        return B.getRoot();
    }

    private final PlaylistRecyclerAdapter.Callback callback = new PlaylistRecyclerAdapter.Callback() {
        @Override
        public void onPlaylistClick(Playlist selectedPlaylist) {
            sharedVM.setViewingplaylist(selectedPlaylist);

            Navigation.findNavController(requireView()).navigate(PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistViewFragment());
        }

    };
}