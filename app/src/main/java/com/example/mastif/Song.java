package com.example.mastif;

public class Song {
    private String songId;
    private String title;
    private String artist;
    private String cover;
    private String previewLink;


    public Song(String songId, String title, String artist, String cover, String previewLink) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.cover = cover;
        this.previewLink = previewLink;
    }

    public String getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getCover() {
        return cover;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }
}