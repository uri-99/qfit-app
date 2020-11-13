package com.example.qfit_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class RoutineListAdapter extends ArrayAdapter<Routine> {

    Context context;
    int resource;
    List<Routine> routineList;

    public RoutineListAdapter(@NonNull Context context, int resource, List<Routine> routineList) {
        super(context, resource, routineList);

        this.context=context;
        this.resource=resource;
        this.routineList=routineList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.routine_as_item, null);
        TextView routineTitle = view.findViewById(R.id.routineTitle);
        TextView routineTrainer = view.findViewById(R.id.routineTrainer);
        TextView routineDescription = view.findViewById(R.id.routineDescription);
        TextView routineDuration = view.findViewById(R.id.routineDuration);

        Routine routine = routineList.get(position);
        routineTitle.setText(routine.getTitle());
        routineDescription.setText(routine.getDescription());
        routineDuration.setText(routine.getDuration());
        routineTrainer.setText(routine.getTrainer());

        view.findViewById(R.id.routineItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routine.createDetails();
                MainActivity.getInstance().appearDetails(routine);
            }
        });

        return view;

    }

    public CycleListAdapter createCycleListAdapter(Context context, int resource, List<Routine.Cycle> cycleList){
        return new CycleListAdapter(context, resource, cycleList);
    }

    public class CycleListAdapter extends ArrayAdapter<Routine.Cycle> {

        Context context;
        int resource;
        List<Routine.Cycle> cycleList;
        Routine routine;

        public CycleListAdapter(@NonNull Context context, int resource, List<Routine.Cycle> cycleList) {
            super(context, resource, cycleList);

            this.context=context;
            this.resource=resource;
            this.cycleList=cycleList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.cycle_as_item, null);
            TextView cycleTitle = view.findViewById(R.id.cycleTitle);
            TextView cycleDescription = view.findViewById(R.id.cycleDescription);
            ListView exerciseList = view.findViewById(R.id.exerciseList);

            Routine.Cycle cycle = cycleList.get(position);
            cycleTitle.setText(cycle.getTitle());
            cycleDescription.setText(cycle.getDescription());
            exerciseList.setAdapter(createExerciseListAdapter(context, resource, cycle.exercises));


            return view;

        }

        public ExerciseListAdapter createExerciseListAdapter(Context context, int resource, List<Exercise> exerciseList){
            return new ExerciseListAdapter(context, resource, exerciseList);
        }

        public class ExerciseListAdapter extends ArrayAdapter<Exercise> {

            Context context;
            int resource;
            List<Exercise> exerciseList;

            public ExerciseListAdapter(@NonNull Context context, int resource, List<Exercise> exerciseList) {
                super(context, resource, exerciseList);

                this.context=context;
                this.resource=resource;
                this.exerciseList=exerciseList;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.exercise_as_item, null);
                TextView exerciseTitle = view.findViewById(R.id.exerciseTitle);
                TextView exerciseReps = view.findViewById(R.id.exerciseReps);

                Exercise exercise = exerciseList.get(position);
                exerciseTitle.setText(exercise.getTitle());
                exerciseReps.setText(exercise.getReps());

                return view;

            }




        }



    }
}
