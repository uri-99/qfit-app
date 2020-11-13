package com.example.qfit_app;

import java.util.List;

public class Routine {

    String title, trainer, description, duration;

    public Routine(String title, String trainer, String description, String duration) {
        this.title = title;
        this.trainer = trainer;
        this.description = description;
        this.duration = duration;
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

    public Cycle createCycle(String title, String description, List<Exercise> exercises) {
        return new Cycle(title, description, exercises);
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
