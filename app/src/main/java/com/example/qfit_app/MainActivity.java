package com.example.qfit_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qfit_app.api.ApiClient;
import com.example.qfit_app.api.classes.RoutineDTO;
import com.example.qfit_app.ui.ui.login.LoginActivity;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    static List<Routine> allRoutineList;
    static List<Routine> favRoutineList;
    private static ListView allRoutinesView;
    private static ListView favRoutinesView;
    private static LinearLayout linearLayoutFilters;
    private static EditText searchBar;
    private static ImageButton searchButton;
    private static ImageButton refreshButton;
    public Button startButton;
    public Button loadExercises;
    Button showSharedRoutine;
    LinearLayout sharedRoutinePopup;
    static ImageButton logoutButton;
    static TextView logoutText;

    private static ConstraintLayout routineDetails;

    private static ListView cycleView;
    private static RoutineListAdapter.CycleListAdapter cycleAdapter;

    private static RoutineListAdapter allAdapter;
    private static RoutineListAdapter favAdapter;

    static ApiClient apiClient;
    Bundle bundle;
    String sharedRoutine = null;
    Boolean found=false;

    private static String direction = "Direction";


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
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        ProgressBar loadingProgressBar = findViewById(R.id.loading);
        Context context = this;
        instance = this;


        refreshButton = findViewById(R.id.refresh);
        searchButton = findViewById(R.id.searchButton);
        searchBar = findViewById(R.id.searchBar);
        linearLayoutFilters = findViewById(R.id.linearLayoutFilters);
        Button welcomeButton = findViewById(R.id.buttonWelcome);
        LinearLayout welcomeMessage = findViewById(R.id.welcomeMessage);
        routineDetails = findViewById(R.id.routineDetails);
        cycleView = findViewById(R.id.cycleList);
        Spinner orderBy = findViewById(R.id.homeOrderBy);
        Button direction = findViewById(R.id.homeOrden);
        direction.setTag(1);
        startButton = findViewById(R.id.routineStartButton);
        loadExercises = findViewById(R.id.buttonLoadExercises);
        sharedRoutinePopup = findViewById(R.id.sharedRoutinePopup);
        showSharedRoutine = findViewById(R.id.buttonShowShared);
        logoutButton = findViewById(R.id.logoutButton);
        logoutText = findViewById(R.id.logoutText);



        searchButton.setVisibility(GONE);
        searchBar.setVisibility(GONE);
        linearLayoutFilters.setVisibility(GONE);
        navView.setVisibility(GONE);

        Intent lastIntent = getIntent();
        bundle = lastIntent.getExtras();
        apiClient = new ApiClient();//(ApiClient) bundle.get("apiClient");
//        apiClient.login(bundle.get("username").toString(), bundle.get("password").toString());

        String action = lastIntent.getAction();
        Uri data = lastIntent.getData();

        if(data != null) {
            Log.d("logg", data.getLastPathSegment());

            Toast.makeText(this, R.string.loginFirst, Toast.LENGTH_SHORT).show();

            Intent loginFirst = new Intent(getApplicationContext(), LoginActivity.class);
            loginFirst.putExtra("sharedRoutine", data.getLastPathSegment());
            startActivity(loginFirst);

        } else {
            if(bundle.get("sharedRoutine")!=null)
                sharedRoutine=bundle.get("sharedRoutine").toString();
            apiClient.login(bundle.get("username").toString(), bundle.get("password").toString());
        }

 //       apiClient.login("johndoe", "1234567890");


        allRoutineList = new ArrayList<>();
        favRoutineList = new ArrayList<>();

        allRoutinesView = findViewById(R.id.allRoutines);
        allAdapter = new RoutineListAdapter(context, R.layout.routine_as_item, allRoutineList, apiClient);
        allRoutinesView.setAdapter(allAdapter);

        favRoutinesView = findViewById(R.id.favRoutines);
        favAdapter = new RoutineListAdapter(context, R.layout.routine_as_item, favRoutineList, apiClient);
        favRoutinesView.setAdapter(favAdapter);


        welcomeMessage.setVisibility(VISIBLE);
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstGet();
                welcomeMessage.setVisibility(GONE);
                navView.setVisibility(View.VISIBLE);

                if(sharedRoutine!=null) {
                    sharedRoutinePopup.setVisibility(VISIBLE);

                    showSharedRoutine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(RoutineDTO routine : apiClient.returnRoutines()){
                                if(String.format("%d",routine.getId()).equals(sharedRoutine)) {
                                    found=true;
                                    apiClient.getRoutineCycles(routine.getId());
                                    appearDetails(new Routine(routine.getName(), routine.getCreator().getUsername(), routine.getDetail(), routine.getDifficulty(), routine.getId(), routine.getAverageRating()));
                                    break;
                                }
                            }
                            if(!found) {
                                Log.d("logg", "routine not found");
                                Toast.makeText(context, R.string.routineNotFound, Toast.LENGTH_SHORT).show();
                            }
                            sharedRoutinePopup.setVisibility(GONE);
                        }
                    });
                }


            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(searchBar.getText().length()>2) {
                    apiClient.setSearchParam(searchBar.getText().toString());
                    apiClient.getRoutines();
                } else if (searchBar.getText().length()==0) {
                    apiClient.setSearchParam(null);
                } else {
                    Toast.makeText(context, "Busqueda debe tener al menos 3 caracteres",Toast.LENGTH_SHORT).show();
                }
                refresh();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBar.getText().length()>2) {
                    apiClient.setSearchParam(searchBar.getText().toString());
                    apiClient.getRoutines();
                } else if (searchBar.getText().length()==0) {
                    apiClient.setSearchParam(null);
                } else {
                    Toast.makeText(context, "Busqueda debe tener al menos 3 caracteres",Toast.LENGTH_SHORT).show();
                }
                refresh();
            }
        });

        orderBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (orderBy.getSelectedItem().toString()) {
                    case "Order by":
                    case "Ordenar por": {
                        apiClient.setOrderByParam(null);
                        break;
                    }
                    case "ID":{
                        apiClient.setOrderByParam("id");
                        break;
                    }
                    case "Name":
                    case "Nombre": {
                        apiClient.setOrderByParam("name");
                        break;
                    }
                    case "Detail":
                    case "Detalle": {
                        apiClient.setOrderByParam("detail");
                        break;
                    }
                    case "Date created":
                    case "Fecha de creación": {
                        apiClient.setOrderByParam("dateCreated");
                        break;
                    }
                    case "Rating": {
                        apiClient.setOrderByParam("averageRating");
                        break;
                    }
                    case "Difficulty":
                    case "Dificultad": {
                        apiClient.setOrderByParam("difficulty");
                        break;
                    }
                    case "Category":
                    case "Categoría": {
                        apiClient.setOrderByParam("categoryId");
                        break;
                    }
                    case "Creator":
                    case "Creador": {
                        apiClient.setOrderByParam("creatorId");
                        break;
                    }
                    default:
                        apiClient.setOrderByParam(null);
                }
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch ((Integer) direction.getTag()) { //puede que tenga que hacer eso de antes para el text
                    case 0: {
                        apiClient.setDirectionParam(null);
                        direction.setTag(1);
                        direction.setText(R.string.direction);


                        break;
                    }
                    case 1: {
                        apiClient.setDirectionParam("asc");
                        direction.setTag(2);
                        direction.setText("asc ↑");
                        break;
                    }
                    case 2: {
                        apiClient.setDirectionParam("desc");
                        direction.setTag(0);
                        direction.setText("desc ↓");
                        break;
                    }
                    default:
                        apiClient.setDirectionParam(null);
                }
                refresh();
            }
        });



/*
        direction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (direction.getSelectedItem().toString()) {
                    case "Direction":
                    case "Dirección": {
                        apiClient.setDirectionParam(null);
                        break;
                    }
                    case "Ascendant":
                    case "Ascendiente": {
                        apiClient.setDirectionParam("asc");
                        break;
                    }
                    case "Descendant":
                    case "Descendiente": {
                        apiClient.setDirectionParam("desc");
                        break;
                    }
                    default:
                        apiClient.setDirectionParam(null);
                }
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiClient.logout();
                Intent logout = new Intent(context, LoginActivity.class);
                startActivity(logout);
            }
        });


    }

    public static void refresh() {
        allAdapter.clear();
        apiClient.getRoutines();
        for(RoutineDTO routine : apiClient.returnRoutines()){
            allAdapter.add(new Routine(routine.getName(), routine.getCreator().getUsername(), routine.getDetail(), routine.getDifficulty(), routine.getId(), routine.getAverageRating()));
        }

        favAdapter.clear();
        apiClient.getFavRoutines();
        for(RoutineDTO routine: apiClient.returnRoutinesFav()){
            favAdapter.add(new Routine(routine.getName(), routine.getCreator().getUsername(), routine.getDetail(), routine.getDifficulty(), routine.getId(), routine.getAverageRating()));
        }
    }

    public static void firstGet() {
        apiClient.getRoutines();
        apiClient.getFavRoutines();
    }

    public static void notified(){
        favAdapter.notifyDataSetChanged();
        allAdapter.notifyDataSetChanged();
    }

    public static MainActivity getInstance(){
        return instance;
    }

    public static void appearAllList() {
        logoutButton.setVisibility(GONE);
        logoutText.setVisibility(GONE);
        searchButton.setVisibility(VISIBLE);
        searchBar.setVisibility(VISIBLE);
        linearLayoutFilters.setVisibility(View.VISIBLE);
        allRoutinesView.setVisibility(View.VISIBLE);
        favRoutinesView.setVisibility(GONE);
        routineDetails.setVisibility(GONE);
        refreshButton.setVisibility(VISIBLE);
    }

    public static void appearFavList() {
        logoutButton.setVisibility(GONE);
        logoutText.setVisibility(GONE);
        searchButton.setVisibility(GONE);
        searchBar.setVisibility(View.GONE);
        linearLayoutFilters.setVisibility(View.GONE);
        allRoutinesView.setVisibility(GONE);
        favRoutinesView.setVisibility(View.VISIBLE);
        routineDetails.setVisibility(GONE);
        refreshButton.setVisibility(VISIBLE);
    }

    public static void disappearLists() {
        logoutButton.setVisibility(VISIBLE);
        logoutText.setVisibility(VISIBLE);
        allRoutinesView.setVisibility(GONE);
        favRoutinesView.setVisibility(GONE);
        routineDetails.setVisibility(GONE);
        searchBar.setVisibility(GONE);
        searchButton.setVisibility(GONE);
        linearLayoutFilters.setVisibility(GONE);
        refreshButton.setVisibility(GONE);

    }

    public void appearDetails(Routine routine) {

        //:adapter
        cycleAdapter = allAdapter.createCycleListAdapter(this, R.layout.cycle_as_item, routine.cycles);
        cycleView.setAdapter(cycleAdapter);
        routine.log();

        searchBar.setVisibility(GONE);
        searchButton.setVisibility(GONE);
        linearLayoutFilters.setVisibility(GONE);
        allRoutinesView.setVisibility(GONE);
        favRoutinesView.setVisibility(GONE);
        routineDetails.setVisibility(View.VISIBLE);
        refreshButton.setVisibility(GONE);


        ImageButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(v -> appearAllList());
        TextView routineDetailTitle = findViewById(R.id.routineDetailTitle);
        routineDetailTitle.setText(routine.getTitle());
        TextView routineDetailTrainer = findViewById(R.id.routineDetailTrainer);
        TextView routineDetailDescription = findViewById(R.id.routineDetailDescription);
        TextView routineDetailDifficulty = findViewById(R.id.routineDetailDifficulty);
        Button showExercises = findViewById(R.id.buttonShowExercises);

        routineDetailTrainer.setText(routine.getTrainer());
        routineDetailDescription.setText(routine.getDescription());
        routineDetailDifficulty.setText(routine.getDuration());


        loadExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadExercises.setVisibility(GONE);
                showExercises.setVisibility(View.VISIBLE);
                apiClient.getExercises(routine.getId());
            }
        });


        showExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExercises.setVisibility(GONE);
                startButton.setVisibility(VISIBLE);
                routine.setExercises(apiClient);
                MainActivity.getInstance().appearDetails(routine);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startRoutine = new Intent(getApplicationContext(), routine_in_progress.class);
                startRoutine.putExtra("routine", routine);
                startRoutine.putExtra("username", bundle.get("username").toString());
                startRoutine.putExtra("password", bundle.get("password").toString());
                startRoutine.putExtra("routineId", routine.getId());

                List<Exercise> cycle1 = routine.cycles.get(0).getExercises();
                startRoutine.putExtra("routineCycle1", (Serializable) cycle1);
                List<Exercise> cycle2 = routine.cycles.get(1).getExercises();
                startRoutine.putExtra("routineCycle2", (Serializable) cycle2);
                List<Exercise> cycle3 = routine.cycles.get(2).getExercises();
                startRoutine.putExtra("routineCycle3", (Serializable) cycle3);

                AlertDialog.Builder dialog = new AlertDialog.Builder(instance, R.style.AlertDialogStyle);
                dialog.setTitle(R.string.selectRoutineType);
                dialog.setPositiveButton(R.string.simple, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRoutine.putExtra("mode", "simple");
                        startActivity(startRoutine);
                    }
                });
                dialog.setNegativeButton(R.string.detailed, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        startRoutine.putExtra("mode", "detailed");
                        startActivity(startRoutine);
                    }
                });
                dialog.create().show(); // Create the Dialog and display it to the user

            }
        });

    }


}
