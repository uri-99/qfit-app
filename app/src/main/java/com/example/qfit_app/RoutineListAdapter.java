package com.example.qfit_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

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

//        view.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //la funcion de la cosa que hace el botón
//            }
//        });

        return view;

    }


}
