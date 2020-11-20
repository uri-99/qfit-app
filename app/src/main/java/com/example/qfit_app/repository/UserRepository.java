package com.example.qfit_app.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.qfit_app.api.ApiResponse;
import com.example.qfit_app.api.ApiService;
import com.example.qfit_app.api.classes.CredentialDTO;
import com.example.qfit_app.api.classes.TokenDTO;
import com.example.qfit_app.db.MyDatabase;
import com.example.qfit_app.vo.AbsentLiveData;
import com.example.qfit_app.vo.Resource;

public class UserRepository {

    private AppExecutors executors;
    private ApiService service;
    private MyDatabase database;

    public UserRepository(AppExecutors executors, ApiService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    public LiveData<Resource<String>> login(String username, String password) {

        return new NetworkBoundResource<String, Void, TokenDTO>(executors,null, null, TokenDTO::getToken) {

            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable TokenDTO model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TokenDTO>> createCall() {
                return service.login(new CredentialDTO(username, password));
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {

        return new NetworkBoundResource<Void, Void, Void>
                (executors, null, null, null) {

            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return service.logout();
            }
        }.asLiveData();
    }
}
