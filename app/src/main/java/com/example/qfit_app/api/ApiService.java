package com.example.qfit_app.api;


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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    //para livedata:
//    @POST("user/login")
//    LiveData<ApiResponse<TokenDTO>> login(@Body CredentialDTO credentials);

    @POST("user/login")
    Call<TokenDTO> login(@Body CredentialDTO credentials);

    @POST("user")
    Call<UserDTO> createUser(@Body NewUserDTO newUser);

    @POST("user/verify_email")
    Call<CodeDTO> verifyEmail(@Body VerifyEmailDTO verifyEmail);

    @GET("user/current")
    Call<UserDTO> getCurrentUser(@Header("authorization") String auth);

    @GET("routines")
    Call<PagedList<RoutineDTO>> getRoutines(@Header("authorization") String auth, @Query("search") String search, @Query("orderBy") String order, @Query("direction") String direction);

    @GET("user/current/routines/favourites")
    Call<PagedList<RoutineDTO>> getFavRoutines(@Header("authorization") String auth);

    @GET("sports")
    Call<PagedList<SportDTO>> getSports(@Header("authorization") String auth);

    @POST("user/current/routines/{routineID}/favourites")
    Call<CodeDTO> markAsFavourite(@Header("authorization") String auth, @Path("routineID") int routineID);

    @DELETE("user/current/routines/{routineID}/favourites")
    Call<CodeDTO> unMarkAsFavourite(@Header("authorization") String auth, @Path("routineID") int routineID);

    @GET("routines/{routineID}/cycles/{cycleID}/exercises")
    Call<PagedList<ExerciseDTO>> getExercises(@Header("authorization") String auth, @Path("routineID") int routineID, @Path("cycleID") int cycleID);

    @POST("routines/{routineId}/ratings")
    Call<RatingDTO> rateRoutine(@Header("authorization") String auth, @Path("routineId") int routineID, @Body RatingDTO ratingCredential);

}
