package com.putttim.mastif.Objects;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String playlistId;
    private String playlistName;
    private String description;
    private List<Song> playlistSongs;

    public Playlist() {
        // Empty constructor required for Firestore for some reason..?
    }

    public Playlist(String playlistId, String playlistName, String description, List<Song> playlistSongs) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.description = description;
        this.playlistSongs = playlistSongs;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getDescription() {
        return description;
    }

    public List<Song> getPlaylistSongs() {
        return playlistSongs;
    }

    public void setPlaylistSongs(List<Song> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }

    public void setId(String playlistId) {
        this.playlistId = playlistId;
    }
}