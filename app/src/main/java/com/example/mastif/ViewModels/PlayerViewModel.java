package com.example.mastif.ViewModels;

import android.media.MediaPlayer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;

public class PlayerViewModel extends ViewModel {
    private MediaPlayer mp = new MediaPlayer();
    MutableLiveData<MediaPlayer> mutMediaPlayer = new MutableLiveData<>(mp);

    public MutableLiveData<MediaPlayer> getPlayer() {
        return mutMediaPlayer;
    }
    public void playerPlay(String songUrl) {
        setPlayingSong(songUrl);
        mp.prepareAsync();
        mp.start();
    }

    public void setPlayingSong(String songUrl) {
        try {
            mp.setDataSource(songUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pauseSong() {
        mp.pause();
    }

}
