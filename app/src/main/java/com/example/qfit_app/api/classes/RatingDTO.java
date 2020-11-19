package com.example.qfit_app.api.classes;


public class RatingDTO {
    float score;
    String review;


    public RatingDTO(String review, float score) {
        this.score = score;
        this.review = review;
    }
}
