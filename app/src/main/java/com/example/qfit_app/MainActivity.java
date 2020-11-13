package com.example.qfit_app;

import android.app.MediaRouteButton;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<Routine> routineList;
  //  ListView listView;
     private static ListView routineView;

     private static List<Routine.Cycle> cycleList;
     private static ListView cycleView;
    private static ConstraintLayout routineDetails;

    List<Exercise> exercises1;
    List<Exercise> exercises2;
 //   private static List<Routine.Cycle.Exercise> exerciseList;
    private static ListView exerciseView;
    private static RoutineListAdapter.CycleListAdapter cycleAdapter;



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

        routineList.add(new Routine("Titulo 1", "entrenador 1", "desc 1", "duracion 1"));
        routineList.add(new Routine("Titulo 2", "entrenador 2", "desc 2", "duracion 2"));
        routineList.add(new Routine("Titulo 3", "entrenador 3", "desc 3", "duracion 3"));
        routineList.add(new Routine("Titulo 4", "entrenador 4", "desc 4", "duracion 4"));
        routineList.add(new Routine("Titulo 5", "entrenador 5", "desc 5", "duracion 5"));
        routineList.add(new Routine("Titulo 6", "entrenador 6", "desc 6", "duracion 6"));


        routineView = findViewById(R.id.myListView);
        RoutineListAdapter adapter = new RoutineListAdapter(this, R.layout.routine_as_item, routineList);
        routineView.setAdapter(adapter);



        List<Exercise> exercisesList1 = new ArrayList<>();
        List<Exercise> exercisesList2 = new ArrayList<>();
        exercisesList1.add(new Exercise("tit1", "tex1"));
        exercisesList1.add(new Exercise("tit4", "tex4"));
        exercisesList2.add(new Exercise("tit2", "tex2"));
        exercisesList2.add(new Exercise("tit3", "tex3"));


        cycleList = new ArrayList<>();
        cycleList.add(routineList.get(0).createCycle("ciclo 1", "texto 1", exercisesList1));
        cycleList.add(routineList.get(0).createCycle("ciclo 2", "texto 2", exercisesList2));
        cycleView = findViewById(R.id.cycleList);
        cycleAdapter = adapter.createCycleListAdapter(this, R.layout.cycle_as_item, cycleList);
        cycleView.setAdapter(cycleAdapter);

        routineDetails = findViewById(R.id.routineDetails);

//        exercises1 = new ArrayList<>();
//        exercises2 = new ArrayList<>();
//        exercises1.add(cycleList.get(0).createExercise("tit1", "tex1"));
//        exercises1.add(cycleList.get(0).createExercise("tit2", "tex2"));
//        exercises2.add(cycleList.get(1).createExercise("tit3", "tex3"));
//        exercises2.add(cycleList.get(1).createExercise("tit4", "tex4"));
//        exerciseList = new ArrayList<>();
//        exerciseList.add(cycleList.get(0).createExercise("tit1", "tex1"));
//        exerciseList.add(cycleList.get(0).createExercise("tit2", "tex2"));
//        exerciseList.add(cycleList.get(1).createExercise("tit3", "tex3"));
//        exerciseList.add(cycleList.get(1).createExercise("tit4", "tex4"));
//        exerciseView = findViewById(R.id.exerciseList);
//   //     Log.d("tagg", exerciseView.toString());
//        RoutineListAdapter.CycleListAdapter.ExerciseListAdapter exerciseAdapter = cycleAdapter.createExerciseListAdapter(this, R.layout.cycle_as_item, exerciseList);
//        exerciseView.setAdapter(exerciseAdapter);


    }

    public static void appearList() {
        routineView.setVisibility(View.VISIBLE);
        routineDetails.setVisibility(View.GONE);
    }
    public static void disappearList() {
        routineView.setVisibility(View.GONE);
        routineDetails.setVisibility(View.VISIBLE);
    }

}