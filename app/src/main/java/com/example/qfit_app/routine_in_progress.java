package com.example.qfit_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class routine_in_progress extends AppCompatActivity {

    TextView routineTitle;
    TextView cycleTitle;
    TextView exerciseTitle;
    TextView exerciseDuration;
    TextView exerciseDescription;
    TextView displayTime;
    TextView finishMessage;
    Button readyButton;
    View divider;
    Routine routine;
    List<Exercise> cycle1;
    List<Exercise> cycle2;
    List<Exercise> cycle3;
    Exercise currentExercise;
    int size1, size2;
    int current1, current2 = 0;
    Timer timerTotal;
    int timeTotal=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_in_progress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cycleTitle = findViewById(R.id.in_progress_cicleTitle);
        exerciseTitle = findViewById(R.id.in_progress_exerciseTitle);
        exerciseDuration = findViewById(R.id.in_progress_exerciseDuration);
        exerciseDescription = findViewById(R.id.in_progress_exerciseDescription);
        displayTime = findViewById(R.id.in_progress_displayTime);
        readyButton = findViewById(R.id.in_progress_buttonReady);
        finishMessage = findViewById(R.id.finishMessage);
        divider = findViewById(R.id.divider);
        routineTitle = findViewById(R.id.in_progress_routineTitle);

        Intent lastIntent = getIntent();
        Bundle bundle = lastIntent.getExtras();
        Routine routine = (Routine) bundle.get("routine");
        routineTitle.setText(routine.getTitle());
        cycle1 = (List<Exercise>) bundle.get("routineCycle1");
        cycle2 = (List<Exercise>) bundle.get("routineCycle2");

        currentExercise = cycle1.get(0);
        size1 = cycle1.size();
        size2 = cycle2.size();

        exerciseTitle.setText(currentExercise.getTitle());
        exerciseDuration.setText(currentExercise.getReps());
        displayTime.setText(currentExercise.getReps());
        cycleTitle.setText("Entrada en calor");
        ViewGroup.LayoutParams size = divider.getLayoutParams();
        size.width= (int) (30*cycleTitle.getText().length());
        divider.setLayoutParams(size);


        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextExercise();
            }
        });
        Button finish = findViewById(R.id.routineFinishButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startRoutine = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startRoutine);
            }
        });

        TextView timerTotalDisplay = findViewById(R.id.timerTotalDisplay);

        timerTotal = new Timer();
        timerTotal.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerTotalDisplay.setText("tiempo total: "+timeTotal);
                        timeTotal++;
                    }
                });
            }
        }, 1000, 1000);

    }

    public void nextExercise(){
        if(current1 < size1-1) {
            current1++;
            currentExercise = cycle1.get(current1);
        } else if(current2 < size2) {
            cycleTitle.setText("Ejercitacion principal");
            ViewGroup.LayoutParams size = divider.getLayoutParams();
            size.width= (int) (30*cycleTitle.getText().length());
            divider.setLayoutParams(size);

            currentExercise = cycle2.get(current2);
            current2++;
        } else {
            timerTotal.cancel();
            timeTotal--; //se le escapa un segundo al final
            String fs;
            fs= String.format("Has completado\n'%s' en %d segundos,\nFelicitaciones!",routineTitle.getText() ,timeTotal);

            finishMessage.setText(fs);
            finishMessage.setVisibility(View.VISIBLE);
            cycleTitle.setVisibility(View.INVISIBLE);
            exerciseTitle.setVisibility(View.INVISIBLE);
            exerciseDuration.setVisibility(View.INVISIBLE);
            exerciseDescription.setVisibility(View.INVISIBLE);
            displayTime.setVisibility(View.INVISIBLE);
            readyButton.setVisibility(View.INVISIBLE);
            divider.setVisibility(View.INVISIBLE);

        }

        exerciseTitle.setText(currentExercise.getTitle());
        exerciseDuration.setText(currentExercise.getReps());
        displayTime.setText(currentExercise.getReps());
    }
}