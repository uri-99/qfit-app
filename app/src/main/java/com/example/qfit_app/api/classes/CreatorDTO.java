package com.example.qfit_app.api.classes;

public class CreatorDTO {
    int id;
    String username;
    String gender;
    String avatarUrl;
    long dateCreated;
    long dateLastActive;

    public CreatorDTO(int id, String username, String gender, String avatarUrl, long dateCreated, long dateLastActive) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public long getDateLastActive() {
        return dateLastActive;
    }
}