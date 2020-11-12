package com.example.qfit_app;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Routine> routineList;
    ListView listView;

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



        listView = findViewById(R.id.myListView);
        RoutineListAdapter adapter = new RoutineListAdapter(this, R.layout.routine_as_item, routineList);
        listView.setAdapter(adapter);
    }

}