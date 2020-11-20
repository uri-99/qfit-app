package com.example.qfit_app.api.classes;

public class CycleDTO {
    int id;
    String name;
    String type;
    int order;
    int repetitions;

    public CycleDTO(int id, String name, String type, int order, int repetitions) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.order = order;
        this.repetitions = repetitions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getOrder() {
        return order;
    }

    public int getRepetitions() {
        return repetitions;
    }
}
