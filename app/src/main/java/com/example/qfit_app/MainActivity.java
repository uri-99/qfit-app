package com.example.qfit_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qfit_app.api.ApiUserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    List<Routine> routineList;
  //  ListView routineView;
     private static ListView routinesView;

     private static List<Routine.Cycle> cycleList;
     private static ListView cycleView;
    private static ConstraintLayout routineDetails;

    List<Exercise> exercises1;
    List<Exercise> exercises2;
 //   private static List<Routine.Cycle.Exercise> exerciseList;
    private static ListView exerciseView;
    private static RoutineListAdapter.CycleListAdapter cycleAdapter;

    private static RoutineListAdapter adapter;
    List<Exercise> exercisesList1;
    List<Exercise> exercisesList2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        routineList = new ArrayList<>();

        routineList.add(new Routine("Rutina 1", "entrenador 1", "desc 1", "duracion 1"));
        routineList.add(new Routine("Rutina 2", "entrenador 2", "desc 2", "duracion 2"));
        routineList.add(new Routine("Rutina 3", "entrenador 3", "desc 3", "duracion 3"));
        routineList.add(new Routine("Rutina 4", "entrenador 4", "desc 4", "duracion 4"));
        routineList.add(new Routine("Rutina 5", "entrenador 5", "desc 5", "duracion 5"));
        routineList.add(new Routine("Rutina 6", "entrenador 6", "desc 6", "duracion 6"));


        routinesView = findViewById(R.id.myListView);
        adapter = new RoutineListAdapter(this, R.layout.routine_as_item, routineList);
        routinesView.setAdapter(adapter);

        routineDetails = findViewById(R.id.routineDetails);
        cycleView = findViewById(R.id.cycleList);

        instance = this;

        try {
            Log.d("logg", ApiUserService.getCurrentUser().execute().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static MainActivity getInstance(){
        return instance;
    }

    public static void appearList() {
        routinesView.setVisibility(View.VISIBLE);
        routineDetails.setVisibility(View.GONE);
    }
    public void appearDetails(Routine routine) {

        cycleAdapter = adapter.createCycleListAdapter(this, R.layout.cycle_as_item, routine.cycles);
        cycleView.setAdapter(cycleAdapter);
        routine.log();

        routinesView.setVisibility(View.GONE);
        routineDetails.setVisibility(View.VISIBLE);


        ImageButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(v -> appearList());
        TextView routineDetailTitle = findViewById(R.id.routineDetailTitle);
        routineDetailTitle.setText(routine.getTitle());

        Button startButton = findViewById(R.id.routineStartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startRoutine = new Intent(getApplicationContext(), routine_in_progress.class);
                startRoutine.putExtra("routine", routine);


                List<Exercise> cycle1 = routine.cycles.get(0).getExercises();
                startRoutine.putExtra("routineCycle1", (Serializable) cycle1);
                List<Exercise> cycle2 = routine.cycles.get(1).getExercises();
                startRoutine.putExtra("routineCycle2", (Serializable) cycle2);

                startActivity(startRoutine);
            }
        });

    }

    public static void disappearLists() {
        routinesView.setVisibility(View.GONE);
        routineDetails.setVisibility(View.GONE);
    }

}