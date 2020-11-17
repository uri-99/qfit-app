package com.example.qfit_app;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    String title, reps, detail;

    public Exercise(String title, String reps, String detail) {
        this.title = title;
        this.reps = reps;
        this.detail=detail;
    }

    protected Exercise(Parcel in) {
        title = in.readString();
        reps = in.readString();
        detail = in.readString();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getReps() {
        return reps;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append(title);
        string.append(reps);
        string.append(detail);
        return string.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(reps);
        dest.writeString(detail);
    }
}