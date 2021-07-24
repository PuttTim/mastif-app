package com.example.mastif.ViewModels;

import android.media.MediaPlayer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mastif.Objects.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerViewModel extends ViewModel {
    private MediaPlayer mp = new MediaPlayer();
    MutableLiveData<MediaPlayer> mutMediaPlayer = new MutableLiveData<>(mp);

    public MutableLiveData<MediaPlayer> getPlayer() {
        return mutMediaPlayer;
    }

    public void addToPlayingList (Song song) {

        try {
            mp.setDataSource(song.getLink());
            Log.d("MASTIF", String.format("%s added to playingList with link: %s", song.getTitle() + song.getLink()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPlayer () {
        mp.prepareAsync();
        mp.start();
    }

    public void pauseSong() {
        mp.pause();
    }

    public void resumeSong() { mp.start();}

}
