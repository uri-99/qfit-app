package com.example.qfit_app;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    String title, reps;

    public Exercise(String title, String reps) {
        this.title = title;
        this.reps = reps;
    }

    protected Exercise(Parcel in) {
        title = in.readString();
        reps = in.readString();
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

    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append(title);
        string.append(reps);
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
    }
}