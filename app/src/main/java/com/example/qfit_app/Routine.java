package com.example.qfit_app;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ListView;

import com.example.qfit_app.api.ApiClient;
import com.example.qfit_app.api.classes.ExerciseDTO;

import java.util.ArrayList;
import java.util.List;

public class Routine implements Parcelable {

    String title, trainer, description, duration;
    List<Cycle> cycles;
    int id;
    boolean second;

    List<Exercise> exercises1 = new ArrayList<>();
    List<Exercise> exercises2 = new ArrayList<>();
    List<Exercise> exercises3 = new ArrayList<>();

    public Routine(String title, String trainer, String description, String duration, int id) {
        this.title = title;
        this.trainer = trainer;
        this.description = description;
        this.duration = duration;
        this.cycles = new ArrayList<>();
        this.id=id;
        second=false;
    }

    protected Routine(Parcel in) {
        title = in.readString();
        trainer = in.readString();
        description = in.readString();
        duration = in.readString();
    }

    public static final Creator<Routine> CREATOR = new Creator<Routine>() {
        @Override
        public Routine createFromParcel(Parcel in) {
            return new Routine(in);
        }

        @Override
        public Routine[] newArray(int size) {
            return new Routine[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTrainer() {
        return trainer;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public List<Cycle> getCycles() {
        Log.d("logg", cycles.get(0).toString());
        return cycles;
    }

    public void log() {
        Log.d("logg", getTitle());
        for(int i=0; i<cycles.size(); i++) {
            Log.d("tagg", cycles.get(i).getTitle());
            for(int j=0; j < cycles.get(i).exercises.size() ; j++) {
                Log.d("tagg", cycles.get(i).exercises.get(j).getTitle());
            }
        }
    }

    public void setExercises(ApiClient apiClient) {

        cycles=new ArrayList<>();

        for(ExerciseDTO exercise : apiClient.cycle1){
            exercises1.add(new Exercise(exercise.getName(), String.format("%d", exercise.getDuration()) ) );
        }
        for(ExerciseDTO exercise : apiClient.cycle2){
            exercises2.add(new Exercise(exercise.getName(), String.format("%d", exercise.getDuration()) ) );
        }
        for(ExerciseDTO exercise : apiClient.cycle3){
            exercises3.add(new Exercise(exercise.getName(), String.format("%d", exercise.getDuration()) ) );
        }

        createCycle("ciclo1", "descr1", exercises1);
        createCycle("ciclo2", "descr2", exercises2);
        createCycle("ciclo3", "descr3", exercises3);

    }

    public Cycle createCycle(String title, String description, List<Exercise> exercises) {
        cycles.add(new Cycle(title, description, exercises));
        return cycles.get(cycles.size() -1); //el que acabo de crear
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(trainer);
        dest.writeString(description);
        dest.writeString(duration);
    }

    public class Cycle {
        String title, description;
        List<Exercise> exercises;

        public Cycle(String title, String description, List<Exercise> exercises) {
            this.title = title;
            this.description = description;
            this.exercises=exercises;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            StringBuilder string = new StringBuilder();
            for(Exercise exercise : exercises) {
                string.append(exercise.getTitle());
                string.append(exercise.getReps());
            }
            return string.toString();
        }

        public String getDescription() {
            return description;
        }

        public List getExercises() {
            return exercises;
        }

        public void addExercise(Exercise exercise) {
            exercises.add(exercise);
        }


    }

}
