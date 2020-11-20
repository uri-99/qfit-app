package com.example.qfit_app;

import android.app.Application;

import androidx.room.Room;

import com.example.qfit_app.api.ApiClient;
import com.example.qfit_app.api.ApiService;
import com.example.qfit_app.db.MyDatabase;
import com.example.qfit_app.repository.AppExecutors;
import com.example.qfit_app.repository.SportRepository;
import com.example.qfit_app.repository.UserRepository;
import com.example.qfit_app.utils.Constants;


public class MyApplication extends Application {

    AppExecutors appExecutors;
    MyPreferences preferences;
    UserRepository userRepository;
     SportRepository sportRepository;

    public MyPreferences getPreferences() {
        return preferences;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SportRepository getSportRepository() {
        return sportRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new MyPreferences(this);

        appExecutors = new AppExecutors();

        ApiService userService = ApiClient.create(this, ApiService.class);
        ApiService sportService = ApiClient.create(this, ApiSportService.class);

        MyDatabase database = Room.databaseBuilder(this, MyDatabase.class, Constants.DATABASE_NAME).build();

        userRepository = new UserRepository(appExecutors, userService, database);

        sportRepository = new SportRepository(appExecutors, sportService, database);
    }
}
