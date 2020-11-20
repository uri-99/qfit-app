package com.example.qfit_app.api;


import androidx.lifecycle.LiveData;

import com.example.qfit_app.api.classes.CodeDTO;
import com.example.qfit_app.api.classes.CredentialDTO;
import com.example.qfit_app.api.classes.ExerciseDTO;
import com.example.qfit_app.api.classes.NewUserDTO;
import com.example.qfit_app.api.classes.PagedList;
import com.example.qfit_app.api.classes.RatingDTO;
import com.example.qfit_app.api.classes.RoutineDTO;
import com.example.qfit_app.api.classes.SportDTO;
import com.example.qfit_app.api.classes.TokenDTO;
import com.example.qfit_app.api.classes.UserDTO;
import com.example.qfit_app.api.classes.VerifyEmailDTO;


import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {


    @POST("user/login")
    LiveData<ApiResponse<TokenDTO>> login(@Body CredentialDTO credentials);

    @POST("user/logout")
    LiveData<ApiResponse<Void>> logout();

    @POST("user")
    LiveData<ApiResponse<UserDTO>> createUser(@Body NewUserDTO newUser);

    @POST("user/verify_email")
    LiveData<ApiResponse<CodeDTO>> verifyEmail(@Body VerifyEmailDTO verifyEmail);

    @GET("user/current")
    LiveData<ApiResponse<UserDTO>> getCurrentUser();

    @GET("routines")
    LiveData<ApiResponse<PagedList<RoutineDTO>>> getRoutines( @Query("search") String search, @Query("orderBy") String order, @Query("direction") String direction);

    @GET("user/current/routines/favourites")
    LiveData<ApiResponse<PagedList<RoutineDTO>>> getFavRoutines();

    @GET("sports")
    LiveData<ApiResponse<PagedList<SportDTO>>> getSports();

    @POST("user/current/routines/{routineID}/favourites")
    LiveData<ApiResponse<CodeDTO>> markAsFavourite(@Header("authorization") String auth, @Path("routineID") int routineID);

    @DELETE("user/current/routines/{routineID}/favourites")
    LiveData<ApiResponse<CodeDTO>> unMarkAsFavourite( @Path("routineID") int routineID);

    @GET("routines/{routineID}/cycles/{cycleID}/exercises")
    LiveData<ApiResponse<PagedList<ExerciseDTO>>> getExercises( @Path("routineID") int routineID, @Path("cycleID") int cycleID);

    @POST("routines/{routineId}/ratings")
    LiveData<ApiResponse<RatingDTO>> rateRoutine( @Path("routineId") int routineID, @Body RatingDTO ratingCredential);






}
