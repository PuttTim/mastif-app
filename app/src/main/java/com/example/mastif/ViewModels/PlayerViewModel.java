package com.example.mastif.ViewModels;

import android.media.MediaPlayer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mastif.Objects.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerViewModel extends ViewModel {
    private MediaPlayer mp;
    MutableLiveData<MediaPlayer> mutMediaPlayer = new MutableLiveData<>(mp);
    private QueueViewModel queueVM;
    public MediaPlayer getMp () {return mp;}

    public void setMediaPlayer(MediaPlayer player) {
        if (mp == null) {
            mp = player;
            Log.d("PlayerVM", "player attached");
        }
    }


    public void addToPlayingList (Song song) {
        if (mp.isPlaying()) {
            mp.reset();
        }
        try {
            mp.setDataSource(song.getLink());
            mp.prepare();
            startPlayer();
            onSongComplete();
            Log.d("LogD PlayerVM", String.format("%s added to playing with link: %s", song.getTitle(), song.getLink()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPlayer () {
        mp.start();
    }

    public void resetPlayer() {
        mp.reset();
    }

    public void onSongComplete() {
        mp.setOnCompletionListener(mp -> resetPlayer());
    }

    public void resumePauseSong() {
        if (!mp.isPlaying()) {
            mp.start();
        }
        else if (mp.isPlaying()) {
            mp.pause();
        }
    }




}
