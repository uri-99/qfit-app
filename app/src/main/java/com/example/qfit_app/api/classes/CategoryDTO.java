package com.example.qfit_app.api.classes;

public class CategoryDTO {
    int id;
    String name;
    String detail;

    public CategoryDTO(int id, String name, String detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
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
}
