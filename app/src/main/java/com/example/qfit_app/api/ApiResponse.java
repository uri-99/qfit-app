package com.example.qfit_app.api;

import android.util.Log;

import com.example.qfit_app.api.classes.ErrorModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ApiResponse<T> {

    private T data;
    private ErrorModel error;

    public T getData() {
        return data;
    }

    public ErrorModel getError() {
        return error;
    }

    public ApiResponse(Response<T> response) {
        parseResponse(response);
    }

    public ApiResponse(Throwable throwable) {
        this.error = buildError(throwable.getMessage());
    }

    private void parseResponse(Response<T> response) {
        if (response.isSuccessful()) {
            this.data = response.body();
            return;
        }

        if (response.errorBody() == null) {
            this.error = buildError("Missing error body");
            return;
        }

        String message;

        try {
            message = response.errorBody().string();
        } catch (IOException exception) {
            Log.e("API", exception.toString());
            this.error = buildError(exception.getMessage());
            return;
        }

        if (message != null && message.trim().length() > 0) {
            Gson gson = new Gson();
            this.error =  gson.fromJson(message, new TypeToken<ErrorModel>() {}.getType());
        }
    }

    private static ErrorModel buildError(String message) {
        ErrorModel error = new ErrorModel(ErrorModel.LOCAL_UNEXPECTED_ERROR, "Unexpected error");
        if (message != null) {
            List<String> details = new ArrayList<>();
            details.add(message);
            error.setDetails(details);
        }
        return error;
    }
}







