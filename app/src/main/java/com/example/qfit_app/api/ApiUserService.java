package com.example.qfit_app.api;

import android.media.session.MediaSession;
import android.net.Credentials;

import com.example.qfit_app.api.classes.Routine;
import com.example.qfit_app.api.classes.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiUserService {

    @POST("user/login")
    Call<MediaSession.Token> login(@Body Credentials credentials);

    @POST("user/logout")
    Call<Void> logout();

    @GET("user/current")
    Call<User> getCurrentUser();
//
//    @GET("user/current/routines/favourites")
//    Call<List<Routine>> getUserFavourites();
//
//    @GET("/routines")
//    Call<PagedList<Routine>> getRoutines();

    
}
