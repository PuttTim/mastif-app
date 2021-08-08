package com.putttim.mastif.Objects;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String playlistId;
    private String playlistName;
    private String description;
    private String playlistCover;
    private List<Song> playlistSongs;

    public Playlist() {
        // Empty constructor required for Firestore for some reason..?
    }

    public Playlist(String playlistId, String playlistName, String description, String playlistCover, List<Song> playlistSongs) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.description = description;
        this.playlistCover = playlistCover;
        this.playlistSongs = playlistSongs;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistCover() { return playlistCover; }

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