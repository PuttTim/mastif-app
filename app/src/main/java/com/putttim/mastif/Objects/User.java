package com.putttim.mastif.Objects;

import java.util.List;

public class User {
    private String userId;
    private String name;
    private String profilePicture;
    private List<Playlist> playlists;

    public User() {
        // Empty constructor required for Firestore for some reason..?
    }

    public User(String userId, String name, String profilePicture, List<Playlist> playlists) {
        this.userId = userId;
        this.name = name;
        this.profilePicture = profilePicture;
        this.playlists = playlists;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }
}
