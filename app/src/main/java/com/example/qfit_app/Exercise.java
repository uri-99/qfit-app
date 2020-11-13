package com.example.qfit_app;

public class Exercise {
    String title, reps;

    public Exercise(String title, String reps) {
        this.title = title;
        this.reps = reps;
    }

    public String getTitle() {
        return title;
    }

    public String getReps() {
        return reps;
    }
}