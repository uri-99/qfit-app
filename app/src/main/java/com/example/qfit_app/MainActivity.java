package com.example.qfit_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

    private static ConstraintLayout routineDetails;

    private static ListView cycleView;
    private static RoutineListAdapter.CycleListAdapter cycleAdapter;

    private static RoutineListAdapter allAdapter;
    private static RoutineListAdapter favAdapter;

    static ApiClient apiClient;
    Bundle bundle;


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

        ProgressBar loadingProgressBar = findViewById(R.id.loading);
        Context context = this;
        instance = this;


        ImageButton refresh = findViewById(R.id.refresh);
        searchButton = findViewById(R.id.searchButton);
        searchBar = findViewById(R.id.searchBar);
        linearLayoutFilters = findViewById(R.id.linearLayoutFilters);
        Button welcomeButton = findViewById(R.id.buttonWelcome);
        LinearLayout welcomeMessage = findViewById(R.id.welcomeMessage);
        routineDetails = findViewById(R.id.routineDetails);
        cycleView = findViewById(R.id.cycleList);
        Spinner orderBy = findViewById(R.id.homeOrderBy);
        Spinner direction = findViewById(R.id.homeOrden);


        searchButton.setVisibility(GONE);
        searchBar.setVisibility(GONE);
        linearLayoutFilters.setVisibility(GONE);
        navView.setVisibility(GONE);

        allRoutineList = new ArrayList<>();
        favRoutineList = new ArrayList<>();

        Intent lastIntent = getIntent();
        bundle = lastIntent.getExtras();
        apiClient = new ApiClient();//(ApiClient) bundle.get("apiClient");
        apiClient.login(bundle.get("username").toString(), bundle.get("password").toString());

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
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if(!orderBy.getSelectedItem().toString().equals("Categoría") && !direction.getSelectedItem().toString().equals("Orden"))
                {
                    apiClient.setOrderByParam(orderBy.getSelectedItem().toString());
                    apiClient.setDirectionParam(direction.getSelectedItem().toString());
                } else if (orderBy.getSelectedItem().toString().equals("Categoría") && direction.getSelectedItem().toString().equals("Orden")) {
                    apiClient.setOrderByParam(null);
                    apiClient.setDirectionParam(null);
                }
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        direction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!orderBy.getSelectedItem().toString().equals("Categoría") && !direction.getSelectedItem().toString().equals("Orden"))
                {
                    apiClient.setOrderByParam(orderBy.getSelectedItem().toString());
                    apiClient.setDirectionParam(direction.getSelectedItem().toString());
                } else if (orderBy.getSelectedItem().toString().equals("Categoría") && direction.getSelectedItem().toString().equals("Orden")) {
                    apiClient.setOrderByParam(null);
                    apiClient.setDirectionParam(null);
                }
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //para liveData
//        loginButton.setOnClickListener(v -> {
//            CredentialDTO credentials = new CredentialDTO("johndoe", "1234567890");
//            MyApplication app = ((MyApplication)getApplication());
//            app.getUserRepository().login(credentials).observe(this,r -> {
//                switch (r.getStatus()) {
//                    case SUCCESS:
//                        Log.d("UI", "success");
//                        AppPreferences preferences = new AppPreferences(app);
//                        preferences.setAuthToken(r.getData().getToken());
//                        binding.result.setText(R.string.success);
//                        binding.getCurrentUserButton.setEnabled(true);
//                        binding.getAllButton.setEnabled(true);
//                        binding.getButton.setEnabled(true);
//                        binding.addButton.setEnabled(true);
//                        break;
//                    default:
//                        defaultResourceHandler(r);
//                        break;
//                }
//            });
//        });


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
        searchButton.setVisibility(VISIBLE);
        searchBar.setVisibility(VISIBLE);
        linearLayoutFilters.setVisibility(View.VISIBLE);
        allRoutinesView.setVisibility(View.VISIBLE);
        favRoutinesView.setVisibility(GONE);
        routineDetails.setVisibility(GONE);
    }

    public static void appearFavList() {
        searchButton.setVisibility(GONE);
        searchBar.setVisibility(View.GONE);
        linearLayoutFilters.setVisibility(View.GONE);
        allRoutinesView.setVisibility(GONE);
        favRoutinesView.setVisibility(View.VISIBLE);
        routineDetails.setVisibility(GONE);
    }

    public static void disappearLists() {
        allRoutinesView.setVisibility(GONE);
        favRoutinesView.setVisibility(GONE);
        routineDetails.setVisibility(GONE);
        searchBar.setVisibility(GONE);
        searchButton.setVisibility(GONE);
        linearLayoutFilters.setVisibility(GONE);
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


        ImageButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(v -> appearAllList());
        TextView routineDetailTitle = findViewById(R.id.routineDetailTitle);
        routineDetailTitle.setText(routine.getTitle());
        TextView routineDetailTrainer = findViewById(R.id.routineDetailTrainer);
        TextView routineDetailDescription = findViewById(R.id.routineDetailDescription);
        TextView routineDetailDifficulty = findViewById(R.id.routineDetailDifficulty);

        routineDetailTrainer.setText(routine.getTrainer());
        routineDetailDescription.setText(routine.getDescription());
        routineDetailDifficulty.setText(routine.getDuration());

        Button startButton = findViewById(R.id.routineStartButton);
        Button loadExercises = findViewById(R.id.buttonLoadExercises);

        loadExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadExercises.setVisibility(GONE);
                startButton.setVisibility(View.VISIBLE);
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


}