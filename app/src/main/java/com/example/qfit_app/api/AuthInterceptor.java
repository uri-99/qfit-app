package com.example.qfit_app.api;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.qfit_app.MyPreferences;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final MyPreferences preferences;

    public AuthInterceptor(Context context) {
        preferences = new MyPreferences(context);
    }

    @NotNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if (preferences.getAuthToken() != null) {
            request.addHeader("Authorization", "Bearer " + preferences.getAuthToken());
        }
        return chain.proceed(request.build());
    }
}
