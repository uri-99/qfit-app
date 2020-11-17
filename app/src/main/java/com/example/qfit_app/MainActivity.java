package com.example.qfit_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qfit_app.api.ApiClient;
import com.example.qfit_app.api.classes.RoutineDTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<RoutineDTO> routineListAll;
    private static List<RoutineDTO> routineListFav;
    private static MainActivity instance;

    private static boolean repeat1=true;
    private static boolean repeat2=true;
    private static boolean again;

    static List<Routine> allRoutineList;
    static List<Routine> favRoutineList;
     private static ListView allRoutinesView;
    private static ListView favRoutinesView;

     private static List<Routine.Cycle> cycleList;
     private static ListView cycleView;
    private static ConstraintLayout routineDetails;

    List<Exercise> exercises1;
    List<Exercise> exercises2;
    private static ListView exerciseView;
    private static RoutineListAdapter.CycleListAdapter cycleAdapter;

    private static RoutineListAdapter allAdapter;
    private static RoutineListAdapter favAdapter;
    List<Exercise> exercisesList1;
    List<Exercise> exercisesList2;

    static ApiClient apiClient;


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

        apiClient = new ApiClient();


        allRoutineList = new ArrayList<>();
        favRoutineList = new ArrayList<>();


//        allRoutineList.add(new Routine("Rutina 1", "entrenador 1", "desc 1", "duracion 1"));
//        allRoutineList.add(new Routine("Rutina 2", "entrenador 2", "desc 2", "duracion 2"));
//        allRoutineList.add(new Routine("Rutina 3", "entrenador 3", "desc 3", "duracion 3"));
//        allRoutineList.add(new Routine("Rutina 22", "entrenador 2", "desc 2", "duracion 2"));
//        allRoutineList.add(new Routine("Rutina 32", "entrenador 3", "desc 3", "duracion 3"));
//        favRoutineList.add(new Routine("Rutina 4", "entrenador 4", "desc 4", "duracion 4"));
//        favRoutineList.add(new Routine("Rutina 5", "entrenador 5", "desc 5", "duracion 5"));
//        favRoutineList.add(new Routine("Rutina 6", "entrenador 6", "desc 6", "duracion 6"));
//        favRoutineList.add(new Routine("Rutina 42", "entrenador 4", "desc 4", "duracion 4"));
//        favRoutineList.add(new Routine("Rutina 52", "entrenador 5", "desc 5", "duracion 5"));
//        favRoutineList.add(new Routine("Rutina 62", "entrenador 6", "desc 6", "duracion 6"));

        allRoutinesView = findViewById(R.id.allRoutines);
        allAdapter = new RoutineListAdapter(this, R.layout.routine_as_item, allRoutineList, apiClient);
        allRoutinesView.setAdapter(allAdapter);

        favRoutinesView = findViewById(R.id.favRoutines);
        favAdapter = new RoutineListAdapter(this, R.layout.routine_as_item, favRoutineList, apiClient);
        favRoutinesView.setAdapter(favAdapter);


        routineDetails = findViewById(R.id.routineDetails);
        cycleView = findViewById(R.id.cycleList);

        instance = this;


        Button login = findViewById(R.id.buttonLogin);
        Button getUser = findViewById(R.id.buttonGetUser);
        ImageButton refresh = findViewById(R.id.refresh);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiClient.login();
            }
        });

        getUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiClient.getRoutines();
                apiClient.getFavRoutines();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                again=false;
                refresh();
            }
        });


    }

    public static void refresh() {
        Log.d("logg", "enter refresh");
        apiClient.getRoutines();
        apiClient.getFavRoutines();
        routineListAll = apiClient.returnRoutines();
        routineListFav = apiClient.returnRoutinesFav();
        fillListAll();
        fillListFav();
    }

    public static void fillListAll() {
        Log.d("logg", "enter fill list");
        for(RoutineDTO routine : routineListAll) {
            repeat1=false;
            for(Routine routine2 : allRoutineList)
            {
                if(routine2.getId() == routine.getId())
                    repeat1=true;
            }
            if(!repeat1)
                allRoutineList.add(new Routine(routine.getName(), routine.getCreator().getUsername(), routine.getDetail(), routine.getDifficulty(), routine.getId()));
        }
    }

    public static void fillListFav() {
        for(RoutineDTO routine : routineListFav) {
            repeat2=false;
            for(Routine routine2 : favRoutineList)
            {
                if(routine2.getId() == routine.getId())
                    repeat2=true;
            }
            if(!repeat2)
                favRoutineList.add(new Routine(routine.getName(), routine.getCreator().getUsername(), routine.getDetail(), routine.getDifficulty(), routine.getId()));
        }
    }

    public static MainActivity getInstance(){
        return instance;
    }

    public static void appearAllList() {
        allRoutinesView.setVisibility(View.VISIBLE);
        favRoutinesView.setVisibility(View.GONE);
        routineDetails.setVisibility(View.GONE);
    }

    public static void appearFavList() {
        allRoutinesView.setVisibility(View.GONE);
        favRoutinesView.setVisibility(View.VISIBLE);
        routineDetails.setVisibility(View.GONE);
    }

    public void appearDetails(Routine routine) {

        //:adapter
        cycleAdapter = allAdapter.createCycleListAdapter(this, R.layout.cycle_as_item, routine.cycles);
        cycleView.setAdapter(cycleAdapter);
        routine.log();

        allRoutinesView.setVisibility(View.GONE);
        favRoutinesView.setVisibility(View.GONE);
        routineDetails.setVisibility(View.VISIBLE);


        ImageButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(v -> appearAllList());
        TextView routineDetailTitle = findViewById(R.id.routineDetailTitle);
        routineDetailTitle.setText(routine.getTitle());

        Button loadExercises = findViewById(R.id.buttonLoadExercises);
        loadExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routine.setExercises(apiClient);
                MainActivity.getInstance().appearDetails(routine);
            }
        });

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
                List<Exercise> cycle3 = routine.cycles.get(2).getExercises();
                startRoutine.putExtra("routineCycle3", (Serializable) cycle3);


                startActivity(startRoutine);
            }
        });

    }

    public static void disappearLists() {
        allRoutinesView.setVisibility(View.GONE);
        favRoutinesView.setVisibility(View.GONE);
        routineDetails.setVisibility(View.GONE);
    }

}