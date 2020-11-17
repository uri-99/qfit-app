package com.example.qfit_app.api.classes;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class CredentialDTO {
    String username, password;

    public CredentialDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

//    public String toString(){
//        String string = String.format("{\n\"username\": \"%s\",\n\"password\": \"%s\"\n}", username, password);
//        Log.d("logg", string);
//        return string;
//    }

}
