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

    // Initial constructor for PlayerVM, with listeners for when the MediaPlayer finishes or is prepared
    public PlayerViewModel() {
        assert mp != null;
        mp.setOnPreparedListener(mp -> startPlayer());
        mp.setOnCompletionListener(mp -> listener.onComplete());
    }

    // Sets the Mutable LiveData Current Song of the Player
    public void setCurrentSong(Song song) { this.mutCurrentSong.setValue(song); }

    // Gets the Mutable LiveData Current Song of the Player
    public MutableLiveData<Song> getCurrentSong() {
        return this.mutCurrentSong;
    }

    public Song getSong() { return this.currentSong; }

    // Gets the Playlist of the
    public List<Song> getPlaylist() {
        return this.playlist.getValue();
    }

    public void setPlaylist(List<Song> sourcePlaylist) {
        // If the shuffle is on then don't reset the playlist and continue using the current one
        // This will most likely get replaced if we add a shufflePlay straight from the playlist.
        //
        // We can also just simply add a setPlaylistShuffle that sets the playlist and shuffles it right away?
        if (shuffleState) {
            return;
        }
        List<Song> destPlaylist = new ArrayList<>(sourcePlaylist);
        playlist.setValue(destPlaylist);
    }

    // Gets current playing song's time
    public int getCurrentSongTime() {
        return mp.getCurrentPosition();
    }

    // Gets the current song's total duration
    public int getSongDuration() {
        return mp.getDuration();
    }

    // Sets the current song's time
    public void setCurrentSongTime(int timeInMs) {
        mp.seekTo(timeInMs);
    }

    // Finds the current Song for comparison later on.
    public Song matchSong() {
        return this.currentPlaylist
                .stream()
                .filter(song -> song.getSongId().equals(this.currentSong.getSongId()))
                .findFirst()
                .orElse(null);
    }

    // Caches the playlist
    public void cachePlaylist(List<Song> passInPlaylist) {
        cachedPlaylist = new ArrayList<>(passInPlaylist);
    }

    // Shuffles the playlist
    public void shufflePlaylist() {
        List<Song> temporaryShufflePlaylist = playlist.getValue();
        assert temporaryShufflePlaylist != null;
        Collections.shuffle(temporaryShufflePlaylist);

        // To allow for the first song inside the new Shuffle playlist to be the current playing song,
        // we remove the song completely from the current playing list and add in the current playing song.
        temporaryShufflePlaylist.remove(currentSong);
        temporaryShufflePlaylist.add(0, currentSong);

        setPlaylist(temporaryShufflePlaylist);
    }

    // Prepares the song for the MediaPlayer
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

    // Plays the previous song, going through a series of Switch ... Case to see if the Player is set to repeat currently
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
                    pausePlayer();
                    setCurrentSongTime(0);
                    reachedStartEndPlaylist = true;
                    listener.onReachEndStartOfPlaylist();
                    break;
                }
                playPrevByIndex(currentIndex);
                break;
        }
    }

    // Passed in index from playPrev() and prepares the song
    public void playPrevByIndex(int currentIndex) {
        if (currentIndex == -1) {
            reachedStartEndPlaylist = true;
            listener.onReachEndStartOfPlaylist();
            return;
        }
        prepareSong(this.currentPlaylist.get(currentIndex));
    }

    // Plays the next song, going through a series of Switch ... Case to see if the Player is set to repeat currently
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

    // Plays the next Song by the passed in Index, whilst checking if the player is on shuffle or not,
    // if it then the player will play the next song and not the current song.
    // (as there is a bug where the "next" song right after shuffle is actually the currently playing song..)
    private void playNextByIndex(int currentIndex) {
        if (shuffleSkipNext) {
            prepareSong(this.currentPlaylist.get(1));
            shuffleSkipNext = false;
            return;
        }
        prepareSong(this.currentPlaylist.get(currentIndex));
    }

    // Starts the music player
    public void startPlayer() {
        mp.start();
        listener.onStarted();
    }

    // Resets the music player
    public void resetPlayer() {
        mp.reset();
    }

    // Pauses the music player
    public void pausePlayer() {
        mp.pause();
        listener.onPaused();
    }

    // Toggles playing or pausing of the music player.
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

    // Toggles shuffle of the playing list.
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

    // Toggles repeat of the music player (repeat playlist, repeat song or no repeating)
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

    // Returns the current shuffle state
    public boolean getShuffleState() {
        return shuffleState;
    }

    // Returns the current repeat state
    public RepeatState getRepeatState() {
        return repeatState;
    }

    // Sets the listener for the PlayerListener
    public void setPlayerListener(PlayerListener listener) {
        this.listener = listener;
    }

    // Interfaces for listening to the methods being executed inside this PlayerVM.
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
