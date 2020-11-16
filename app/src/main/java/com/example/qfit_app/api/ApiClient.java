package com.example.qfit_app.api;

import android.util.Log;

import com.example.qfit_app.api.classes.CredentialDTO;
import com.example.qfit_app.api.classes.UserDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private ApiService apiService;

    public ApiClient() {

         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.1.197:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         apiService = retrofit.create(ApiService.class);
    }

    public void getCurrentUser() {
        Call<UserDTO> reloadCall = apiService.getCurrentUser("bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjEsImlhdCI6MTYwNTU0OTAxNjkyMSwiZXhwIjoxNjA1NTUxNjA4OTIxfQ.zSId4N5K_LJ--ZcM_HYqN1MZYOP_83BHpyy7tkUscKY");
        reloadCall.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                Log.d("logg", "enter getcurrentuser");
                Log.d("logg", response.toString());
//                UserDTO user = response.body();
//                Log.d("logg", user.toString());
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.d("logg", "failure getcurrentuser");
                call.cancel();
            }
        });
    }

    public void login() {
        Call<UserDTO> loginCall = apiService.login(new CredentialDTO("johndoe", "1234567890"));
        loginCall.enqueue((new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                Log.d("logg", "enterlogin");
                Log.d("logg", response.toString());
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.d("logg", "failurelogin");
            }
        }));
    }

    public void getRoutines() {
        Call<UserDTO> loginCall = apiService.getRoutines("bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjEsImlhdCI6MTYwNTU0OTAxNjkyMSwiZXhwIjoxNjA1NTUxNjA4OTIxfQ.zSId4N5K_LJ--ZcM_HYqN1MZYOP_83BHpyy7tkUscKY");

        loginCall.enqueue((new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                Log.d("logg", "enter getroutines");
                Log.d("logg", response.body().toString());
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.d("logg", "failure getroutines");
            }
        }));
    }


}
