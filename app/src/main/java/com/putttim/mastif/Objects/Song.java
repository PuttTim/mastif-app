package com.putttim.mastif.Objects;

public class Song {
    private String songId;
    private String title;
    private String artist;
    private String cover;
    private String link;

    public Song() {
        // no argument constructor
    }

    public Song(String songId, String title, String artist, String cover, String link) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.cover = cover;
        this.link = link;
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

    public String getLink() {
        return link;
    }

}