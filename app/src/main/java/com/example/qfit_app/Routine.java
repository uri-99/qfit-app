package com.example.qfit_app;

import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Routine {

    String title, trainer, description, duration;
    List<Cycle> cycles;

    public Routine(String title, String trainer, String description, String duration) {
        this.title = title;
        this.trainer = trainer;
        this.description = description;
        this.duration = duration;
        this.cycles = new ArrayList<>();
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

    public void log() {
        Log.d("tagg", getTitle());
        for(int i=0; i<cycles.size(); i++) {
            Log.d("tagg", cycles.get(i).getTitle());
            for(int j=0; j < cycles.get(i).exercises.size() ; j++) {
                Log.d("tagg", cycles.get(i).exercises.get(j).getTitle());
            }
        }
    }

    public void createDetails(){
//esto enrealidad estaria chupando los datos de la api, metiendo los ejercicios de cada ciclo de la rutina correspondiente
        List<Exercise> exercises1 = new ArrayList<>();
        List<Exercise> exercises2 = new ArrayList<>();
        cycles=new ArrayList<>();
        exercises1.add(new Exercise("tit1", "tex1"));
        exercises1.add(new Exercise("tit2", "tex2"));
        exercises2.add(new Exercise("tit3", "tex3"));
        exercises2.add(new Exercise("tit4", "tex4"));

        createCycle("ciclo1", "descr1", exercises1);
        createCycle("ciclo2", "descr2", exercises2);

//        cycles.get(0).addExercise(new Exercise("tit1", "tex1"));
//        cycles.get(0).addExercise(new Exercise("tit2", "tex2"));
//        cycles.get(1).addExercise(new Exercise("tit3", "tex3"));
//        cycles.get(1).addExercise(new Exercise("tit4", "tex4"));
    }

    public Cycle createCycle(String title, String description, List<Exercise> exercises) {
        cycles.add(new Cycle(title, description, exercises));
        return cycles.get(cycles.size() -1); //el que acabo de crear
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

        public String getDescription() {
            return description;
        }

        public List getExercises() {
            return exercises;
        }

        public void addExercise(Exercise exercise) {
            exercises.add(exercise);
        }


//        public Exercise createExercise(String title, String reps){
//            return new Exercise(title, reps);
//        }
//
//        public class Exercise {
//            String title, reps;
//
//            public Exercise(String title, String reps) {
//                this.title = title;
//                this.reps = reps;
//            }
//
//            public String getTitle() {
//                return title;
//            }
//
//            public String getReps() {
//                return reps;
//            }
//        }

    }

}
