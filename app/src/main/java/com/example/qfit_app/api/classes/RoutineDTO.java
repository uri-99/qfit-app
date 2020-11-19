package com.example.qfit_app.api.classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoutineDTO implements Serializable {

    @Expose
    @SerializedName("id")
    int id;

    @Expose
    @SerializedName("name")
    String name;

    @Expose
    @SerializedName("detail")
    String detail;

    @Expose
    @SerializedName("dateCreated")
    long dateCreated;

    @Expose
    @SerializedName("averageRating")
    float averageRating;

    @Expose
    @SerializedName("isPublic")
    boolean isPublic;

    @Expose
    @SerializedName("difficulty")
    String difficulty;

    @Expose
    @SerializedName("creator")
    CreatorDTO creator;

    @Expose
    @SerializedName("category")
    CategoryDTO category;

    public RoutineDTO(int id, String name, String detail, long dateCreated, int averageRating, boolean isPublic, String difficulty, CreatorDTO creator, CategoryDTO category) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.dateCreated = dateCreated;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.creator = creator;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public CreatorDTO getCreator() {
        return creator;
    }

    public CategoryDTO getCategory() {
        return category;
    }
}