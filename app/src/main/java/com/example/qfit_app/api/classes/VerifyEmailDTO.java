package com.example.qfit_app.api.classes;

public class VerifyEmailDTO {
    private String email;
    private String code;

    public VerifyEmailDTO(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
