package com.example.mastif.ViewModels;

import android.media.MediaPlayer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mastif.Objects.Song;

import java.io.IOException;
import java.util.List;

public class PlayerViewModel extends ViewModel {
    MutableLiveData<List<Song>> songs = new MutableLiveData<>();
    MutableLiveData<Song> mutCurrentSong = new MutableLiveData<>();
    MediaPlayer mp = new MutableLiveData<>(new MediaPlayer()).getValue();
    private Song currentSong;
    PlayerListener listener;

    public PlayerViewModel() {
        assert mp != null;
        mp.setOnPreparedListener(mp -> startPlayer());
        mp.setOnCompletionListener(mp -> listener.onComplete());
    }

    public MutableLiveData<Song> getCurrentSong() {
        return mutCurrentSong;
    }

    public void selectSong(Song song) {
        mutCurrentSong.setValue(song);
    }

    public void prepareSong(Song song) {
        try {
            this.currentSong = song;
            mp.reset();
            mp.setDataSource(this.currentSong.getLink());
            mp.prepare();
            listener.onPrepared();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playNext() {

    }

    public void startPlayer () {
        mp.start();
        listener.onStarted();
    }

    public void resetPlayer() {
        mp.reset();
    }

    public void pausePlayer() {
        mp.pause();
        listener.onPaused();
    }

    public void togglePlayPause() {
        if (!mp.isPlaying()) {
            startPlayer();
            return;
        }
        pausePlayer();
    }

    public interface PlayerListener {
        void onStarted();
        void onPaused();
        void onComplete();
        void onPrepared();
        void onNext();
    }

    public void setPlayerListener(PlayerListener listener) {
        this.listener = listener;
    }

}
