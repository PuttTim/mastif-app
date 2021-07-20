package com.example.mastif;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String playlistId;
    private String playlistName;
    private List<Song> playlistContent;


    private Playlist(String playlistId, String playlistName, List<Song> playlistContent) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.playlistContent = playlistContent;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public List<Song> getPlaylistContent() {
        return playlistContent;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void setPlaylistContent(List<Song> playlistContent) {
        this.playlistContent = playlistContent;
    }
}
