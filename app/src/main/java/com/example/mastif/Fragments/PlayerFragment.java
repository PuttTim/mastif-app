package com.example.mastif.Fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.Matrix;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mastif.Objects.Song;
import com.example.mastif.R;
import com.example.mastif.ViewModels.PlayerViewModel;
import com.example.mastif.databinding.FragmentPlayerBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding B;
    private PlayerViewModel playerVM;
    private ObjectAnimator rotateAnimation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        B = FragmentPlayerBinding.inflate(inflater, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        playerVM = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        // Each line is a different button onClickListener which calls the method inside PlayerViewModel
        B.btnPlayPause.setOnClickListener(v -> playerVM.togglePlayPause());
        B.btnForward.setOnClickListener(v -> playerVM.playNext());
        B.btnPrevious.setOnClickListener(v -> playerVM.playPrev());
        B.btnShuffle.setOnClickListener(v -> playerVM.toggleShuffle());

        rotateAnimation = ObjectAnimator.ofFloat(B.imgCover, View.ROTATION, 0.0f, 360.0f);

        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        playerVM.setPlayerListener(new PlayerViewModel.PlayerListener() {
            @Override
            public void onStarted() {
                B.btnPlayPause.setImageResource(R.drawable.ic_pause_button_circle);
                rotateAnimation.resume();
            }

            @Override
            public void onPaused() {
                B.btnPlayPause.setImageResource(R.drawable.ic_play_button_circle);
                rotateAnimation.pause();
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
                rotateAnimation.setDuration(playerVM.getCurrentSongTime() / 4);
                rotateAnimation.start();
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
                if (playerVM.getShuffleState()) {
                    B.btnShuffle.setColorFilter(requireActivity().getColor(R.color.primaryPurple));
                    return;
                }
                B.btnShuffle.setColorFilter(requireActivity().getColor(R.color.white));
            }
        });

        playerVM.prepareSong(playerVM.getCurrentSong().getValue());

        // Inflate the layout for this fragment
        return B.getRoot();
    }
}