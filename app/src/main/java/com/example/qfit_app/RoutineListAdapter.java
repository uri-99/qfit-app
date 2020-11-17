package com.example.qfit_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.qfit_app.api.ApiClient;

import org.w3c.dom.Text;

import java.util.List;

public class RoutineListAdapter extends ArrayAdapter<Routine> {

    Context context;
    int resource;
    List<Routine> routineList;
    ApiClient apiClient;

    public RoutineListAdapter(@NonNull Context context, int resource, List<Routine> routineList, ApiClient apiClient) {
        super(context, resource, routineList);

        this.context=context;
        this.resource=resource;
        this.routineList=routineList;
        this.apiClient = apiClient;

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
                apiClient.getExercises(routine.getId());
                MainActivity.getInstance().appearDetails(routine);
            }
        });



        ImageButton buttonFav = view.findViewById(R.id.buttonFav);
        ImageButton buttonRemFav = view.findViewById(R.id.buttonRemFav);

        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiClient.markAsFavourite(routine.getId());
                Toast.makeText(context, "added to favourites",Toast.LENGTH_SHORT).show();
            }
        });
        buttonRemFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiClient.unMarkAsFavourite(routine.getId());
                Toast.makeText(context, "rutina removida",Toast.LENGTH_SHORT).show();

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
            ViewGroup.LayoutParams listSize = exerciseList.getLayoutParams();

            listSize.height=103*exerciseList.getCount();
            exerciseList.setLayoutParams(listSize);


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
