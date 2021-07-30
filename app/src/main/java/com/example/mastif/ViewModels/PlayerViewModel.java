package com.example.mastif.ViewModels;

import android.media.MediaPlayer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mastif.Objects.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PlayerViewModel extends ViewModel {
    MutableLiveData<List<Song>> playlist = new MutableLiveData<>();
    MutableLiveData<Song> mutCurrentSong = new MutableLiveData<>();
    MediaPlayer mp = new MutableLiveData<>(new MediaPlayer()).getValue();
    PlayerListener listener;
    private Song currentSong;
    private List<Song> currentPlaylist;
    private List<Song> cachedPlaylist;
    private boolean reachedStartEndPlaylist = false;
    private boolean shuffleState = false;
    private boolean shuffleSkipNext = false;

    public PlayerViewModel() {
        assert mp != null;
        mp.setOnPreparedListener(mp -> startPlayer());
        mp.setOnCompletionListener(mp -> listener.onComplete());
    }

    public void selectSong(Song song) { mutCurrentSong.setValue(song); }

    public MutableLiveData<Song> getCurrentSong() {
        return mutCurrentSong;
    }

    public Song getSong() { return this.currentSong; }

    public List<Song> getPlaylist() {
        return playlist.getValue();
    }

    public void setPlaylist(List<Song> sourcePlaylist) {
        List<Song> destPlaylist = new ArrayList<>(sourcePlaylist);
        playlist.setValue(destPlaylist);
    }

    public long getCurrentSongTime() {
        return mp.getDuration();
    }

    public Song matchSong() {
        return this.currentPlaylist
                .stream()
                .filter(song -> song.getSongId().equals(this.currentSong.getSongId()))
                .findFirst()
                .orElse(null);
    }

    public void cachePlaylist(List<Song> passInPlaylist) {
        cachedPlaylist = new ArrayList<>(passInPlaylist);
    }

    public void shufflePlaylist() {
        List<Song> temporaryShufflePlaylist = playlist.getValue();
        assert temporaryShufflePlaylist != null;
        Collections.shuffle(temporaryShufflePlaylist);
        temporaryShufflePlaylist.remove(currentSong);
        temporaryShufflePlaylist.add(0, currentSong);

        setPlaylist(temporaryShufflePlaylist);
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
            reachedStartEndPlaylist = true;
            listener.onReachEndStartOfPlaylist();
            return;
        }
        prepareSong(this.currentPlaylist.get(currentIndex));
    }

    public void playNext() {
        this.currentPlaylist = playlist.getValue();
        if (shuffleSkipNext) {
            assert this.currentPlaylist != null;
            prepareSong(this.currentPlaylist.get(1));
            shuffleSkipNext = false;
            return;
        }
        int currentIndex = this.currentPlaylist.indexOf(matchSong()) + 1;
        if (currentIndex >= this.currentPlaylist.size()) {
            reachedStartEndPlaylist = true;
            listener.onReachEndStartOfPlaylist();
            return;
        }
        prepareSong(this.currentPlaylist.get(currentIndex));
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
        if (reachedStartEndPlaylist) {
            prepareSong(currentPlaylist.get(0));
            reachedStartEndPlaylist = false;
        }
        if (mp.isPlaying()) {
            pausePlayer();
            return;
        }
        startPlayer();
    }

    public void toggleShuffle() {

        if (this.shuffleState) {
            this.shuffleState = false;
            listener.onShuffleToggle();
            setPlaylist(cachedPlaylist);
            return;
        }
        this.shuffleState = true;
        cachePlaylist(this.currentPlaylist);
        listener.onShuffleToggle();
        shufflePlaylist();
    }

    public boolean getShuffleState() {
        return shuffleState;
    }

    public void setPlayerListener(PlayerListener listener) {
        this.listener = listener;
    }

    public interface PlayerListener {
        void onStarted();

        void onPaused();

        void onComplete();

        void onPrepared();

        void onReachEndStartOfPlaylist();

        void onShuffleToggle();
    }

}
