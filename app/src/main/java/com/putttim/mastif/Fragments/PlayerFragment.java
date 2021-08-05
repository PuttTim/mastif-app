package com.putttim.mastif.Fragments;

import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;
import android.widget.Toast;

import com.putttim.mastif.R;
import com.putttim.mastif.ViewModels.PlayerViewModel;
import com.putttim.mastif.databinding.FragmentPlayerBinding;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding B;
    private PlayerViewModel playerVM;
    private ObjectAnimator rotateAnimation;
    private Handler handler;

    private final PlayerViewModel.PlayerListener OnPlayerListener = new PlayerViewModel.PlayerListener() {
        @Override
        public void onStarted() {
            B.btnPlayPause.setImageResource(R.drawable.ic_pause_button_circle);
            rotateAnimation.resume();
            handler.postDelayed(handlerSeekBar, 50);
            B.seekBar.setProgress(playerVM.getCurrentSongTime());
        }

        @Override
        public void onPaused() {
            B.btnPlayPause.setImageResource(R.drawable.ic_play_button_circle);
            rotateAnimation.pause();
            handler.removeCallbacks(handlerSeekBar);
        }

        @Override
        public void onComplete() {
            playerVM.playNext();
        }

        @Override
        public void onPrepared() {
            Picasso.get().load(playerVM.getSong().getCover())
                    .transform(new CropCircleTransformation())
                    .into(B.imgCover);

            B.txtTitle.setText(playerVM.getSong().getTitle());
            B.txtArtist.setText(playerVM.getSong().getArtist());
            rotateAnimation.setDuration(playerVM.getSongDuration() / 4);
            rotateAnimation.start();
            B.totalTime.setText(String.format("%s:%s",
                    ((playerVM.getSongDuration() / 1000) / 60),
                    (playerVM.getSongDuration() / 1000) % 60));
            B.seekBar.setMax(playerVM.getSongDuration());
        }

        @Override
        public void onReachEndStartOfPlaylist() {
            Toast.makeText(requireContext(), "You have been sent to the start of the playlist!", Toast.LENGTH_SHORT).show();
            B.btnPlayPause.setImageResource(R.drawable.ic_play_button_circle);
            playerVM.resetPlayer();
            Picasso.get().load(playerVM.getPlaylist().get(0).getCover())
                    .transform(new CropCircleTransformation())
                    .into(B.imgCover);
            B.txtTitle.setText(playerVM.getPlaylist().get(0).getTitle());
            B.txtArtist.setText(playerVM.getPlaylist().get(0).getArtist());
            rotateAnimation.end();
        }

        @Override
        public void onShuffleToggle() {
            setShuffleButtonColor();
        }

        @Override
        public void onRepeatToggle() {
            setRepeatButtonColor();
        }
    };

    private final SeekBar.OnSeekBarChangeListener OnSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            B.currentTime.setText(String.format("%s:%s",
                    ((playerVM.getCurrentSongTime() / 1000) / 60),
                    (playerVM.getCurrentSongTime() / 1000) % 60));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeCallbacks(handlerSeekBar);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.postDelayed(handlerSeekBar, 50);
            playerVM.setCurrentSongTime(seekBar.getProgress());

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlayerBinding.inflate(inflater, container, false);
        handler = new Handler(Looper.getMainLooper());
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        // Each line is a different button onClickListener which calls the method inside PlayerViewModel
        B.btnPlayPause.setOnClickListener(v -> playerVM.togglePlayPause());
        B.btnForward.setOnClickListener(v -> playerVM.playNext());
        B.btnPrevious.setOnClickListener(v -> playerVM.playPrev());
        B.btnShuffle.setOnClickListener(v -> playerVM.toggleShuffle());
        B.btnRepeat.setOnClickListener(v -> playerVM.toggleRepeat());


        playerVM.setPlayerListener(OnPlayerListener);

        B.seekBar.setOnSeekBarChangeListener(OnSeekBarListener);

        // Gets the repeat and shuffle button state and sets the color of them onCreateView of the fragment
        setRepeatButtonColor();
        setShuffleButtonColor();

        // Rotation animation for the song image in the middle of the player.
        rotateAnimation = ObjectAnimator.ofFloat(B.imgCover, View.ROTATION, 0.0f, 360.0f);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());


        // These are all the listeners that are defined inside PlayerVM.

        // This will call prepareSong() when the player gets launched, thus, playing the song. **HAS TO BE AFTER LISTENER**
        playerVM.prepareSong(playerVM.getCurrentSong().getValue());

        // Inflate the layout for this fragment
        return B.getRoot();
    }

    private void setShuffleButtonColor() {
        if (playerVM.getShuffleState()) {
            B.btnShuffle.setColorFilter(requireActivity().getColor(R.color.primaryPurple));
            return;
        }
        B.btnShuffle.setColorFilter(requireActivity().getColor(R.color.white));
    }

    private void setRepeatButtonColor() {
        switch(playerVM.getRepeatState()) {
            case OFF:
                B.btnRepeat.setImageResource(R.drawable.ic_repeat_button_off);
                B.btnRepeat.setColorFilter(requireActivity().getColor(R.color.white));
                break;
            case REPEAT_PLAYLIST:
                B.btnRepeat.setImageResource(R.drawable.ic_repeat_button_playlist);
                B.btnRepeat.setColorFilter(requireActivity().getColor(R.color.primaryPurple));
                break;
            default:
                B.btnRepeat.setImageResource(R.drawable.ic_repeat_button_song);
                B.btnRepeat.setColorFilter(requireActivity().getColor(R.color.primaryPurple));
                break;
        }
    }

    Runnable handlerSeekBar = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 50);

            B.seekBar.setProgress(playerVM.getCurrentSongTime());

        }
    };
}