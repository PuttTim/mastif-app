package com.example.mastif.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mastif.Objects.Song;
import com.example.mastif.R;
import com.example.mastif.ViewModels.PlayerViewModel;
import com.example.mastif.databinding.FragmentPlayerBinding;
import com.squareup.picasso.Picasso;


public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding B;
    private PlayerViewModel playerVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlayerBinding.inflate(inflater, container, false);

        playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        // Each line is a different button onClickListener which calls the method inside PlayerViewModel
        B.btnPlayPause.setOnClickListener(v -> playerVM.togglePlayPause());
        B.btnForward.setOnClickListener(v -> playerVM.playNext());
        B.btnPrevious.setOnClickListener(v -> playerVM.playPrev());

        playerVM.setPlayerListener(new PlayerViewModel.PlayerListener() {
            @Override
            public void onStarted() {
                B.btnPlayPause.setImageResource(R.drawable.ic_pause_button_circle);
            }

            @Override
            public void onPaused() {
                B.btnPlayPause.setImageResource(R.drawable.ic_play_button_circle);
            }

            @Override
            public void onComplete() {
                playerVM.playNext();
            }

            @Override
            public void onPrepared() {
                Picasso.get().load(playerVM.getSong().getCover()).into(B.imgCover);
                B.txtTitle.setText(playerVM.getSong().getTitle());
                B.txtArtist.setText(playerVM.getSong().getArtist());
            }

            @Override
            public void onNext() {

            }

            @Override
            public void onReachEndStartOfPlaylist() {
                Toast.makeText(requireContext(), "End of playlist reached", Toast.LENGTH_SHORT).show();
                B.btnPlayPause.setImageResource(R.drawable.ic_play_button_circle);
                playerVM.resetPlayer();
                Picasso.get().load(playerVM.getPlaylist().get(0).getCover()).into(B.imgCover);
                B.txtTitle.setText(playerVM.getPlaylist().get(0).getTitle());
                B.txtArtist.setText(playerVM.getPlaylist().get(0).getArtist());
//                B.btnPlayPause.setOnClickListener(v -> playerVM.prepareSong(playerVM.getPlaylist().get(0)));
            }


        });

//        currentSong = playerVM.getCurrentSong().getValue();

        playerVM.prepareSong(playerVM.getCurrentSong().getValue());

        // Inflate the layout for this fragment
        return B.getRoot();
    }
}