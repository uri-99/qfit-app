package com.example.qfit_app;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.qfit_app.utils.Constants;


public class MyPreferences {
    private final String AUTH_TOKEN = "auth_token";
    private final SharedPreferences sharedPreferences;

    public MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }
}
