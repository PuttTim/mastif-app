package com.putttim.mastif.Objects;

import java.util.List;

public class User {
    private String userId;
    private String name;
    private String profilePicture;

    public User() {
        // Empty constructor required for Firestore for some reason..?
    }

    public User(String userId, String name, String profilePicture) {
        this.userId = userId;
        this.name = name;
        this.profilePicture = profilePicture;
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
}
