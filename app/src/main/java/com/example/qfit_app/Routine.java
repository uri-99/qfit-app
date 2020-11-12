package com.example.qfit_app;

public class Routine {

    String title, trainer, description, duration;

    public Routine(String title, String trainer, String description, String duration) {
        this.title = title;
        this.trainer = trainer;
        this.description = description;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getTrainer() {
        return trainer;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }
}
