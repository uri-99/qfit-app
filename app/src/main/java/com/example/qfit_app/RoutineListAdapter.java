package com.example.qfit_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qfit_app.api.ApiClient;
import com.example.qfit_app.ui.home.HomeFragment;

import java.util.List;

public class RoutineListAdapter extends ArrayAdapter<Routine> {

    Context context;
    int resource;
    List<Routine> routineList;
    ApiClient apiClient;

    String title;
    String desc;

    public RoutineListAdapter(@NonNull Context context, int resource, List<Routine> routineList, ApiClient apiClient) {
        super(context, resource, routineList);

        this.context = context;
        this.resource = resource;
        this.routineList = routineList;
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
        TextView routineRating = view.findViewById(R.id.routineRating);
        Routine routine = routineList.get(position);

       // view.findViewById(R.id.buttonRemFav).setVisibility(View.GONE);

        title = routine.getTitle();
        if(title.length() >= 20){
            title = title.substring(0, 19);
            title = title.concat("...");
        }

        routineTitle.setText(title);

        desc = routine.getDescription();

        if(desc.length() >= 35){
            desc = desc.substring(0, 34);
            desc = desc.concat("...");
        }

        routineDescription.setText(desc);
        routineDuration.setText(routine.getDuration());
        routineTrainer.setText(routine.getTrainer());
        routineRating.setText(routine.getRating().toString());

        view.findViewById(R.id.routineItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiClient.getExercises(routine.getId());
                MainActivity.getInstance().appearDetails(routine);
                MainActivity.getInstance().startButton.setVisibility(View.GONE);
                MainActivity.getInstance().loadExercises.setVisibility(View.VISIBLE);
            }
        });



        ImageButton buttonFav = view.findViewById(R.id.buttonFav);
        ImageButton buttonRemFav = view.findViewById(R.id.buttonRemFav);

        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiClient.markAsFavourite(routine.getId());
                Toast.makeText(context, R.string.routineAdded,Toast.LENGTH_SHORT).show();
                MainActivity.refresh();
            }
        });

        buttonRemFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //la villereada maxima: hacer desaparecer la rutina
                //y hay que borrar la rutina 2 veces por alguna razon misteriosa de este codigo trambólico
        //        view.setVisibility(View.GONE);
                routineList.remove(routine);
                apiClient.unMarkAsFavourite(routine.getId());
                Toast.makeText(context, R.string.routineRemoved,Toast.LENGTH_SHORT).show();
                MainActivity.refresh();
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
