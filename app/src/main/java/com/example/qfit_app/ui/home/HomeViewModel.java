package com.example.qfit_app.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qfit_app.MainActivity;
import com.example.qfit_app.R;
import com.example.qfit_app.Routine;
import com.example.qfit_app.RoutineListAdapter;
import com.example.qfit_app.api.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private ApiClient apiClient = new ApiClient();

    private List<Routine> allRoutineList = new ArrayList<>();
    private List<Routine> favRoutineList = new ArrayList<>();



    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
       // mText.setValue("Acá aparecerán tus rutinas favoritas");
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public List<Routine> getAllRoutineList() {
        return allRoutineList;
    }

    public void setAllRoutineList(List<Routine> allRoutineList) {
        this.allRoutineList = allRoutineList;
    }

    public List<Routine> getFavRoutineList() {
        return favRoutineList;
    }

    public void setFavRoutineList(List<Routine> favRoutineList) {
        this.favRoutineList = favRoutineList;
    }

}