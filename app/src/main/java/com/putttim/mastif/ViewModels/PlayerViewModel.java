package com.putttim.mastif.ViewModels;

import android.media.MediaPlayer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.putttim.mastif.Objects.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public enum RepeatState {REPEAT_SONG, REPEAT_PLAYLIST, OFF}
    RepeatState repeatState = RepeatState.OFF;

    //TODO Type out comments explaining what every method does (one or two lines?)

    public PlayerViewModel() {
        assert mp != null;
        mp.setOnPreparedListener(mp -> startPlayer());
        mp.setOnCompletionListener(mp -> listener.onComplete());
    }

    public void setSong(Song song) { mutCurrentSong.setValue(song); }

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

    public int getCurrentSongTime() {
        return mp.getCurrentPosition();
    }

    public int getSongDuration() {
        return mp.getDuration();
    }

    public void setCurrentSongTime(int timeInMs) {
        mp.seekTo(timeInMs);
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
        switch (repeatState) {
            case REPEAT_PLAYLIST:
                if (currentIndex == -1) { playNextByIndex(0); break; }
                playPrevByIndex(currentIndex);
                break;
            case REPEAT_SONG:
                prepareSong(this.currentSong);
                break;
            case OFF:
                if (currentIndex == -1)     {
                    reachedStartEndPlaylist = true;
                    listener.onReachEndStartOfPlaylist();
                    break;
                }
                playPrevByIndex(currentIndex);
                break;
        }
    }

    public void playPrevByIndex(int currentIndex) {
        if (currentIndex == -1) {
            reachedStartEndPlaylist = true;
            listener.onReachEndStartOfPlaylist();
            return;
        }
        prepareSong(this.currentPlaylist.get(currentIndex));
    }

    public void playNext() {
        this.currentPlaylist = playlist.getValue();
        assert this.currentPlaylist != null;
        int currentIndex = this.currentPlaylist.indexOf(matchSong()) + 1;

        switch (repeatState) {
            case REPEAT_PLAYLIST:
                if (currentIndex >= this.currentPlaylist.size()) { playNextByIndex(0); break; }
                playNextByIndex(currentIndex);
                break;
            case REPEAT_SONG:
                prepareSong(this.currentSong);
                break;
            case OFF:
                if (currentIndex >= this.currentPlaylist.size()) {
                    reachedStartEndPlaylist = true;
                    listener.onReachEndStartOfPlaylist();
                    break;
                }
                playNextByIndex(currentIndex);
                break;
        }
    }

    private void playNextByIndex(int currentIndex) {
        if (shuffleSkipNext) {
            prepareSong(this.currentPlaylist.get(1));
            shuffleSkipNext = false;
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
        cachePlaylist(playlist.getValue());
        listener.onShuffleToggle();
        shufflePlaylist();
    }

    public void toggleRepeat() {
        switch (repeatState) {
            case OFF:
                repeatState = RepeatState.REPEAT_PLAYLIST;
                break;
            case REPEAT_PLAYLIST:
                repeatState = RepeatState.REPEAT_SONG;
                break;
            default:
                repeatState = RepeatState.OFF;
                break;
        }
        listener.onRepeatToggle();
    }

    public boolean getShuffleState() {
        return shuffleState;
    }

    public RepeatState getRepeatState() {
        return repeatState;
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

        void onRepeatToggle();
    }

}
