package com.example.mastif.ViewModels;

import android.media.MediaPlayer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mastif.Objects.Song;

import java.io.IOException;
import java.util.List;

public class PlayerViewModel extends ViewModel {
    MutableLiveData<List<Song>> playlist = new MutableLiveData<>();
    MutableLiveData<Song> mutCurrentSong = new MutableLiveData<>();
    MediaPlayer mp = new MutableLiveData<>(new MediaPlayer()).getValue();
    private Song currentSong;
    private List<Song> currentPlaylist;
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

    public void setPlaylist(List<Song> songList) {
        playlist.setValue(songList);
    }

    public List<Song> getPlaylist() {
        return playlist.getValue();
    }

//    public Song getNextSong() {
//        this.currentPlaylist = playlist.getValue();
//        int currentIndex = this.currentPlaylist.indexOf(matchSong()) + 1;
//        if (currentIndex >= this.currentPlaylist.size()) {
//            currentIndex = 0;
//            return this.currentPlaylist.get(currentIndex);
//        }
//        return this.currentSong;
//    }

    public Song matchSong() {
        return this.currentPlaylist
                .stream()
                .filter(song -> song.getSongId().equals(this.currentSong.getSongId()))
                .findFirst()
                .orElse(null);
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

    public void playPrev() {
        this.currentPlaylist = playlist.getValue();
        int currentIndex = this.currentPlaylist.indexOf(matchSong()) - 1;
        if (currentIndex == -1) {
            listener.onReachStartOfPlaylist();
            return;
        }
        prepareSong(this.currentPlaylist.get(currentIndex));
//        listener.onPrevious();
    }

    public void playNext() {
        this.currentPlaylist = playlist.getValue();
        int currentIndex = this.currentPlaylist.indexOf(matchSong()) + 1;
        if (currentIndex >= this.currentPlaylist.size()) {
            listener.onReachEndOfPlaylist();
            return;
        }
        prepareSong(this.currentPlaylist.get(currentIndex));
//        listener.onNext();
    }

    public void startPlayer() {
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
        if (mp.isPlaying()) {
            pausePlayer();
            return;
        }
        startPlayer();
    }

    public interface PlayerListener {
        void onStarted();
        void onPaused();
        void onComplete();
        void onPrepared();
        void onNext();
        void onReachEndOfPlaylist();
        void onReachStartOfPlaylist();
    }

    public void setPlayerListener(PlayerListener listener) {
        this.listener = listener;
    }

}
