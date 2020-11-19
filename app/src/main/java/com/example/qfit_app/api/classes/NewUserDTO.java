package com.example.qfit_app.api.classes;

public class NewUserDTO {
    private final String username;
    private String password = "";
    private final String fullName="default";
    private final String email;
    private final long  birthdate = 284007600000L;
    private final String gender = "male";

    public NewUserDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}