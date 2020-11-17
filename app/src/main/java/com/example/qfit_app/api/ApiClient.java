package com.example.qfit_app.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.qfit_app.BuildConfig;
import com.example.qfit_app.Exercise;
import com.example.qfit_app.MainActivity;
import com.example.qfit_app.Routine;
import com.example.qfit_app.api.classes.CodeDTO;
import com.example.qfit_app.api.classes.CredentialDTO;
import com.example.qfit_app.api.classes.ExerciseDTO;
import com.example.qfit_app.api.classes.PagedList;
import com.example.qfit_app.api.classes.RoutineDTO;
import com.example.qfit_app.api.classes.SportDTO;
import com.example.qfit_app.api.classes.TokenDTO;
import com.example.qfit_app.api.classes.UserDTO;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiClient implements Parcelable {
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

    public ApiClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().
                setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.1.197:8080/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         apiService = retrofit.create(ApiService.class);


    }


    protected ApiClient(Parcel in) {
        token = in.readString();
    }

    public static final Creator<ApiClient> CREATOR = new Creator<ApiClient>() {
        @Override
        public ApiClient createFromParcel(Parcel in) {
            return new ApiClient(in);
        }

        @Override
        public ApiClient[] newArray(int size) {
            return new ApiClient[size];
        }
    };

    public void login(String username, String password) {
        Call<TokenDTO> loginCall = apiService.login(new CredentialDTO(username, password));

        loginCall.enqueue((new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                Log.d("logg", "enterlogin");
                Log.d("logg", response.body().getToken());
                token = String.format("bearer %s", response.body().getToken());
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable t) {
                Log.d("logg", "failurelogin");
            }
        }));
    }

    public void getCurrentUser() {
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

    public String getToken(){
        return token;
    }
    public void setToken(String newToken){
        token=newToken;
    }


    public void getRoutines() {
        Call<PagedList<RoutineDTO>> loginCall = apiService.getRoutines(token);


        loginCall.enqueue(new Callback<PagedList<RoutineDTO>>() {
            @Override
            public void onResponse(Call<PagedList<RoutineDTO>> call, Response<PagedList<RoutineDTO>> response) {
                Log.d("logg", "enter getroutines");
//                Log.d("logg", response.body().getOrderBy());
//                updateRoutineList(response.body().getResults());
                routineListAll=response.body().getResults();
            }

            @Override
            public void onFailure(Call<PagedList<RoutineDTO>> call, Throwable t) {
                Log.d("logg", "failure getroutines");
            }

        });
    }

    public void getFavRoutines() {
        Call<PagedList<RoutineDTO>> loginCall = apiService.getFavRoutines(token);

        loginCall.enqueue(new Callback<PagedList<RoutineDTO>>() {
            @Override
            public void onResponse(Call<PagedList<RoutineDTO>> call, Response<PagedList<RoutineDTO>> response) {
                Log.d("logg", "enter getfavroutines");
//                Log.d("logg", response.body().getOrderBy());
//                updateRoutineList(response.body().getResults());
                routineListFav=response.body().getResults();
            }

            @Override
            public void onFailure(Call<PagedList<RoutineDTO>> call, Throwable t) {
                Log.d("logg", "failure getroutines");
            }

        });
    }


    public List<RoutineDTO> returnRoutines(){
        Log.d("logg", "database returned");
        return routineListAll;
    }

    public List<RoutineDTO> returnRoutinesFav(){
        Log.d("logg", "fav database returned");
        return routineListFav;
    }

    public void markAsFavourite(int routineID){
        Call<CodeDTO> favCall = apiService.markAsFavourite(token, routineID);

        favCall.enqueue(new Callback<CodeDTO>() {
            @Override
            public void onResponse(Call<CodeDTO> call, Response<CodeDTO> response) {
                Log.d("logg", "faved");
            }

            @Override
            public void onFailure(Call<CodeDTO> call, Throwable t) {
                Log.d("logg", "faved fail");
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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
    }
}
