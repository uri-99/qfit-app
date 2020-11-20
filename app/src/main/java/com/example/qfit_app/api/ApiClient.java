package com.example.qfit_app.api;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.qfit_app.BuildConfig;
import com.example.qfit_app.Exercise;
import com.example.qfit_app.MainActivity;
import com.example.qfit_app.R;
import com.example.qfit_app.Routine;
//import com.example.qfit_app.api.LiveData.ApiResponse;
//import com.example.qfit_app.api.LiveData.LiveDataCallAdapterFactory;
//import com.example.qfit_app.api.LiveData.NetworkBoundResource;
//import com.example.qfit_app.api.LiveData.Resource;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiClient{
    private static final String BASE_URL = "http://10.0.1.197:8080/api/";
    private ApiService apiService;
    public String token = "emptys";
    public List<RoutineDTO> routineListAll;
    public List<RoutineDTO> routineListFav;

    public List<ExerciseDTO> cycle1;
    public List<ExerciseDTO> cycle2;
    public List<ExerciseDTO> cycle3;

    public static final int CONNECT_TIMEOUT = 60;
    public static final int READ_TIMEOUT = 60;
    public static final int WRITE_TIMEOUT = 60;

    public String searchParam = null;
    public String orderByParam = null;
    public String directionParam = null;

    public static Context context;

    public ApiClient() {

    }
    public static <S> S create(Context context, Class<S> serviceClass) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().
                setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(context))
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new ApiDateTypeAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();

        return retrofit.create(serviceClass);
    }




    public void setContext(Context context) {
        this.context=context;
    }



   /* public void login(String username, String password) {


        Call<TokenDTO> loginCall = apiService.login(new CredentialDTO(username, password));

        loginCall.enqueue((new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                if(response.code() == 200) {
                    Log.d("logg", "enterlogin");
                    Log.d("logg", response.body().getToken());
                    token = String.format("bearer %s", response.body().getToken());

                } else {
                    token = "invalid";
                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show();
                   // throw new ArithmeticException("invalid credentials");
                }
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable t) {
                Log.d("logg", "failurelogin");
            }
        }));
    }*/




   /* public void getCurrentUser() {
        Call<UserDTO> currentUserCall = apiService.getCurrentUser(token);
        currentUserCall.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                Log.d("logg", "enter getcurrentuser");
                Log.d("logg", token);
                Log.d("logg", response.body().getFullName());
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.d("logg", "fail getcurrentuser");
                call.cancel();
            }
        });
    }

    public void createUser(String username, String password, String email){
        Call<UserDTO> newUser = apiService.createUser(new NewUserDTO(username,password,email));
        newUser.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                Log.d("logg", "Falta la verificacion");
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.d("logg","Error al crear el usuario");
            }
        });

    }

    public void verifyEmail(String email, String code) {
        Call<CodeDTO> verify = apiService.verifyEmail(new VerifyEmailDTO(email, code));
        verify.enqueue(new Callback<CodeDTO>() {
            @Override
            public void onResponse(Call<CodeDTO> call, Response<CodeDTO> response) {
                if(response.code() == 200) {
                    Toast.makeText(context, R.string.correctCode, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.incorrectCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CodeDTO> call, Throwable t) {
                Log.d("logg", "failverify");
            }
        });
    }
*/
    /*public String getToken(){
        return token;
    }
    public void setToken(String newToken){
        token=newToken;
    }


    public void getRoutines() {
        Call<PagedList<RoutineDTO>> getRoutines = apiService.getRoutines(token, searchParam, orderByParam, directionParam);


        getRoutines.enqueue(new Callback<PagedList<RoutineDTO>>() {
            @Override
            public void onResponse(Call<PagedList<RoutineDTO>> call, Response<PagedList<RoutineDTO>> response) {
                Log.d("logg", "enter getroutines");
//                Log.d("logg", response.body().getOrderBy());
//                updateRoutineList(response.body().getResults());
                routineListAll=response.body().getResults();

                MainActivity.notified();
            }

            @Override
            public void onFailure(Call<PagedList<RoutineDTO>> call, Throwable t) {
                Log.d("logg", "failure getroutines");
            }

        });
    }

    public void getFavRoutines() {
        Call<PagedList<RoutineDTO>> getRoutines = apiService.getFavRoutines(token);

        getRoutines.enqueue(new Callback<PagedList<RoutineDTO>>() {
            @Override
            public void onResponse(Call<PagedList<RoutineDTO>> call, Response<PagedList<RoutineDTO>> response) {
                Log.d("logg", "enter getfavroutines");
//                Log.d("logg", response.body().getOrderBy());
//                updateRoutineList(response.body().getResults());
                routineListFav=response.body().getResults();

                MainActivity.notified();
            }

            @Override
            public void onFailure(Call<PagedList<RoutineDTO>> call, Throwable t) {
                Log.d("logg", "failure getroutines");
            }

        });
    }


    public List<RoutineDTO> returnRoutines(){
        Log.d("logg", "database returned");

        MainActivity.notified();
        return routineListAll;
    }

    public List<RoutineDTO> returnRoutinesFav(){
        Log.d("logg", "fav database returned");

        MainActivity.notified();
        return routineListFav;
    }

    public void markAsFavourite(int routineID){
        Call<CodeDTO> favCall = apiService.markAsFavourite(token, routineID);

        favCall.enqueue(new Callback<CodeDTO>() {
            @Override
            public void onResponse(Call<CodeDTO> call, Response<CodeDTO> response) {
                Log.d("logg", "faved");

                MainActivity.refresh();
            }

            @Override
            public void onFailure(Call<CodeDTO> call, Throwable t) {
                Log.d("logg", "faved fail");
            }
        });

    }

    public void unMarkAsFavourite(int routineID){
        Call<CodeDTO> unFavCall = apiService.unMarkAsFavourite(token, routineID);

        unFavCall.enqueue(new Callback<CodeDTO>() {
            @Override
            public void onResponse(Call<CodeDTO> call, Response<CodeDTO> response) {
                Log.d("logg", "remove faved");

                MainActivity.refresh();
            }

            @Override
            public void onFailure(Call<CodeDTO> call, Throwable t) {
                Log.d("logg", "remfaved fail");
            }
        });

    }

    public void getExercises(int routineID) {
        Call<PagedList<ExerciseDTO>> cycle1Call = apiService.getExercises(token, routineID, 1);

        cycle1Call.enqueue(new Callback<PagedList<ExerciseDTO>>() {
            @Override
            public void onResponse(Call<PagedList<ExerciseDTO>> call, Response<PagedList<ExerciseDTO>> response) {
                Log.d("logg", "enter getExercises1");
                cycle1=response.body().getResults();
                Log.d("logg", cycle1.toString());
            }

            @Override
            public void onFailure(Call<PagedList<ExerciseDTO>> call, Throwable t) {
                Log.d("logg", "failure getroutines");
            }

        });

        Call<PagedList<ExerciseDTO>> cycle2Call = apiService.getExercises(token, routineID, 2);
        cycle2Call.enqueue(new Callback<PagedList<ExerciseDTO>>() {
            @Override
            public void onResponse(Call<PagedList<ExerciseDTO>> call, Response<PagedList<ExerciseDTO>> response) {
                Log.d("logg", "enter getExercises2");
                cycle2=response.body().getResults();
            }

            @Override
            public void onFailure(Call<PagedList<ExerciseDTO>> call, Throwable t) {
                Log.d("logg", "failure getroutines");
            }

        });

        Call<PagedList<ExerciseDTO>> cycle3Call = apiService.getExercises(token, routineID, 3);
        cycle3Call.enqueue(new Callback<PagedList<ExerciseDTO>>() {
            @Override
            public void onResponse(Call<PagedList<ExerciseDTO>> call, Response<PagedList<ExerciseDTO>> response) {
                Log.d("logg", "enter getExercises3");
                cycle3=response.body().getResults();
            }

            @Override
            public void onFailure(Call<PagedList<ExerciseDTO>> call, Throwable t) {
                Log.d("logg", "failure getroutines");
            }

        });

    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public void setOrderByParam(String orderByParam) {
        this.orderByParam = orderByParam;
    }

    public void setDirectionParam(String directionParam) {
        this.directionParam = directionParam;
    }

    public void rateRoutine(int routineID, RatingDTO ratingCredentials){
        Call<RatingDTO> rateRoutineCall = apiService.rateRoutine(token, routineID, ratingCredentials);

        rateRoutineCall.enqueue((new Callback<RatingDTO>() {
            @Override
            public void onResponse(Call<RatingDTO> call, Response<RatingDTO> response) {
                Log.d("logg", "Scored correctly");

            }

            @Override
            public void onFailure(Call<RatingDTO> call, Throwable t) {
                Log.d("logg", "something went wrong rating");

            }
        }));
    }


    public void getSports() {
        Call<PagedList<SportDTO>> sportsCall = apiService.getSports(token);

        sportsCall.enqueue((new Callback<PagedList<SportDTO>>() {
            @Override
            public void onResponse(Call<PagedList<SportDTO>> call, Response<PagedList<SportDTO>> response) {
                Log.d("logg", "enter gersports");
                Log.d("logg", response.body().getDirection());
            }

            @Override
            public void onFailure(Call<PagedList<SportDTO>> call, Throwable t) {
                Log.d("logg", "fail getsports");

            }
        }));
    }*/

}
