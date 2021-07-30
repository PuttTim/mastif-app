package com.example.mastif.ViewModels;

import android.media.MediaPlayer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mastif.Objects.Song;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayerViewModel extends ViewModel {
    MutableLiveData<List<Song>> playlist = new MutableLiveData<>();
    MutableLiveData<List<Song>> mutCachePlaylist = new MutableLiveData<>();
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

    public void setPlaylist(List<Song> songList) {
        playlist.setValue(songList);
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
        mutCachePlaylist.setValue(passInPlaylist);
        Log.d("LogD PlayerVM", String.format("cachePlaylist() cache %s",mutCachePlaylist.getValue()));
        Log.d("LogD PlayerVM", String.format("cachePlaylist() current %s",playlist.getValue()));
    }

    public void shufflePlaylist() {
        List<Song> temporaryShufflePlaylist = playlist.getValue();
        assert temporaryShufflePlaylist != null;
        Log.d("LogD PlayerVM", String.format("shufflePlaylist() temp before %s", temporaryShufflePlaylist) );
        Log.d("LogD PlayerVM", String.format("shufflePlaylist() cache before %s",mutCachePlaylist.getValue()));
        Log.d("LogD PlayerVM", String.format("shufflePlaylist() current before %s",playlist.getValue()));
//        Collections.shuffle(this.currentPlaylist);
        Collections.shuffle(temporaryShufflePlaylist);
        temporaryShufflePlaylist.remove(currentSong);
        temporaryShufflePlaylist.add(0, currentSong);

        setPlaylist(temporaryShufflePlaylist);
        Log.d("LogD PlayerVM", String.format("shufflePlaylist() temp after %s", temporaryShufflePlaylist) );
        Log.d("LogD PlayerVM", String.format("shufflePlaylist() cache after %s",mutCachePlaylist.getValue()));
        Log.d("LogD PlayerVM", String.format("shufflePlaylist() current after %s",playlist.getValue()));

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

        if (!this.shuffleState) {
            cachePlaylist(playlist.getValue());
            this.shuffleState = true;
            listener.onShuffleToggle();
            shufflePlaylist();
            return;
        }
        if (this.shuffleState) {
            this.shuffleState = false;
            listener.onShuffleToggle();
            Log.d("LogD PlayerVM", String.format("toggleShuffle cache before %s",cachedPlaylist));
            Log.d("LogD PlayerVM", String.format("toggleShuffle current before %s",currentPlaylist));
            setPlaylist(mutCachePlaylist.getValue());
            Log.d("LogD PlayerVM", String.format("toggleShuffle cache after %s",cachedPlaylist));
            Log.d("LogD PlayerVM", String.format("toggleShuffle current after %s",playlist.getValue()));
        }

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
