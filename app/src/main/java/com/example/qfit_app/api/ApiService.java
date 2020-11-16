package com.example.qfit_app.api;

import com.example.qfit_app.api.classes.CredentialDTO;
import com.example.qfit_app.api.classes.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiService {

    @GET("user/current")
    Call<UserDTO> getCurrentUser(@Header("authorization") String credentials);

    @POST("user/login")
    Call<UserDTO> login(@Body CredentialDTO credentials);

    @GET("routines")
    Call<UserDTO> getRoutines(@Header("authorization") String auth);

//    @POST("user/login")
//    Call<MediaSession.Token> login(@Body Credentials credentials);
//
//    @POST("user/logout")
//    Call<Void> logout();
//
//    @GET("user/current")
//    Call<UserDTO> getCurrentUser();
////
////    @GET("user/current/routines/favourites")
////    Call<List<RoutineDTO>> getUserFavourites();
////
////    @GET("/routines")
////    Call<PagedList<RoutineDTO>> getRoutines();
//
//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/json"
//    })
//    @GET("user/current")
//    Call<RoutineDTO> getRoutine();

}
