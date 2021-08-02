package com.putttim.mastif.Objects;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String playlistId;
    private String playlistName;
    private List<Song> playlistContent = new ArrayList<Song>();

    public Playlist(String playlistId, String playlistName, List<Song> playlistContent) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.playlistContent = playlistContent;
    }

    // Getters
    public String getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public List<Song> getPlaylistContent() {
        return playlistContent;
    }

    // Setters
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void setPlaylistContent(List<Song> playlistContent) {
        this.playlistContent = playlistContent;
    }

    public Song getSong(int songId) {
        return playlistContent.get(songId);
    }

    public void removeSong(int songId) {
        System.out.printf("Removing song: %s from %s\n", playlistName, getSong(songId).getTitle());
        playlistContent.remove(songId);
    }

    public int getPlaylistSize() {
        return playlistContent.size();
    }
}
