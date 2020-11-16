package com.example.qfit_app.api.classes;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class CredentialDTO {
    String user, password;

    public CredentialDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String toString(){
        String string = String.format("{\n\"username\": \"%s\",\n\"password\": \"%s\"\n}", user, password);
        Log.d("logg", string);
        return string;
    }

}
