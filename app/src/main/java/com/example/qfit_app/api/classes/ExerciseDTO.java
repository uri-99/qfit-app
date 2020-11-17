package com.example.qfit_app.api.classes;

public class ExerciseDTO {
    int id;
    String name;
    String detail;
    String type;
    int duration;
    int repetitons;
    int order;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public String getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public int getRepetitons() {
        return repetitons;
    }

    public int getOrder() {
        return order;
    }
}
