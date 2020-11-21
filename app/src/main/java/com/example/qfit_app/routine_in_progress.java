package com.example.qfit_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qfit_app.api.ApiClient;
import com.example.qfit_app.api.classes.RatingDTO;

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
    Button rateBtn;
    RatingBar ratingBar;
    View divider;
    Routine routine;
    List<Exercise> cycle1;
    List<Exercise> cycle2;
    List<Exercise> cycle3;
    Exercise currentExercise;
    int size1, size2, size3;
    int current1, current2, current3 = 0;
    Timer timerTotal;
    int timeTotal=1;
    int i=1;
    ApiClient apiClient;
    private static routine_in_progress instance;
    boolean finished=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_in_progress);
        cycleTitle = findViewById(R.id.in_progress_cicleTitle);
        exerciseTitle = findViewById(R.id.in_progress_exerciseTitle);
        exerciseDuration = findViewById(R.id.in_progress_exerciseDuration);
        exerciseDescription = findViewById(R.id.in_progress_exerciseDescription);
        displayTime = findViewById(R.id.in_progress_displayTime);
        readyButton = findViewById(R.id.in_progress_buttonReady);
        finishMessage = findViewById(R.id.finishMessage);
        divider = findViewById(R.id.divider);
        routineTitle = findViewById(R.id.in_progress_routineTitle);
        rateBtn = findViewById(R.id.rate_btn);
        ratingBar = findViewById(R.id.ratingBar);

        instance=this;

        Intent lastIntent = getIntent();
        Bundle bundle = lastIntent.getExtras();
        Routine routine = (Routine) bundle.get("routine");
        routineTitle.setText(routine.getTitle());
        cycle1 = (List<Exercise>) bundle.get("routineCycle1");
        cycle2 = (List<Exercise>) bundle.get("routineCycle2");
        cycle3 = (List<Exercise>) bundle.get("routineCycle3");

        apiClient = new ApiClient();
            apiClient.login(bundle.get("username").toString(), bundle.get("password").toString());

        String mode = bundle.get("mode").toString();
        if(mode.equals("simple") ) {
            exerciseDescription.setVisibility(View.GONE);
        } else if(mode.equals("detailed")){
            exerciseDescription.setVisibility(View.VISIBLE);
        }

        currentExercise = cycle1.get(0);
        size1 = cycle1.size();
        size2 = cycle2.size();
        size3 = cycle3.size();

        String str = new String("x" + currentExercise.getReps());
        exerciseTitle.setText(currentExercise.getTitle());
        exerciseDuration.setText(str);
        exerciseDescription.setText(currentExercise.getDetail());
        displayTime.setText(currentExercise.getReps());
        cycleTitle.setText(R.string.cycle1Title);
        ViewGroup.LayoutParams size = divider.getLayoutParams();
        size.width = (int) (30*cycleTitle.getText().length());
        divider.setLayoutParams(size);


        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextExercise();
                i=1; //Porque sino, cuando se termina una, las siguientes arrancan en 0.
            }
        });

        Button finish = findViewById(R.id.routineFinishButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!finished) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(instance,  R.style.AlertDialogRed);
                    dialog.setTitle(R.string.confirmExit);
                    dialog.setMessage(R.string.confirmExitSubtitle);
                    dialog.setPositiveButton(R.string.affirmative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent endRoutine = new Intent(getApplicationContext(), MainActivity.class);
                            endRoutine.putExtra("username", bundle.get("username").toString());
                            endRoutine.putExtra("password", bundle.get("password").toString());
                            startActivity(endRoutine);
                        }
                    });
                    dialog.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
                    dialog.create().show(); // Create the Dialog and display it to the user
                } else {
                    Intent endRoutine = new Intent(getApplicationContext(), MainActivity.class);
                    endRoutine.putExtra("username", bundle.get("username").toString());
                    endRoutine.putExtra("password", bundle.get("password").toString());
                    startActivity(endRoutine);
                }
            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     ratingBar.setClickable(false);
                ratingBar.setEnabled(false);
                rateBtn.setVisibility(View.GONE);
                rateRoutine((Integer) bundle.get("routineId"));
                Toast.makeText(routine_in_progress.this, "Rutina puntuada!", Toast.LENGTH_SHORT).show();
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
                        timerTotalDisplay.setText(getResources().getString(R.string.totalTimeMessage) + " " + timeTotal);
                        timeTotal++;

                        if(i > 0) {
                            i=Integer.parseInt((String) displayTime.getText());
                            i--;
                            displayTime.setText(String.format("%d", i));
                        } else {
                           displayTime.setText(R.string.exerciseReadyMessage);
                        }


                    }
                });
            }
        }, 1000, 1000);

    }

    public void rateRoutine(int routineId){
        float rate = ratingBar.getRating();
        RatingDTO creds = new RatingDTO("default text", rate);
        apiClient.rateRoutine(routineId, creds);
    }

  //  @SuppressLint("DefaultLocale")
    public void nextExercise(){
        if(current1 < size1-1) {
            current1++;
            currentExercise = cycle1.get(current1);
        } else if(current2 < size2) {
            cycleTitle.setText(R.string.cycle2Title);
            ViewGroup.LayoutParams size = divider.getLayoutParams();
            size.width= (int) (30*cycleTitle.getText().length());
            divider.setLayoutParams(size);

            currentExercise = cycle2.get(current2);
            current2++;
        } else if(current3 < size3) {
            cycleTitle.setText(R.string.cycle3Title);
            ViewGroup.LayoutParams size = divider.getLayoutParams();
            size.width= (int) (30*cycleTitle.getText().length());
            divider.setLayoutParams(size);

            currentExercise = cycle3.get(current3);
            current3++;

        } else {
            timerTotal.cancel();
            timeTotal--; //se le escapa un segundo al final
            String fs = new String();
            fs = String.format("%s %s %s %d %s \n %s", getResources().getString(R.string.finishRoutineMessage1), routineTitle.getText(), getResources().getString(R.string.finishRoutineMessage2), timeTotal, getResources().getString(R.string.finishRoutineMessage3), getResources().getString(R.string.finishRoutineMessage4));


            LinearLayout linearLayout = findViewById(R.id.routine_finish);
            linearLayout.setVisibility(View.VISIBLE);

            finishMessage.setText(fs);
            finishMessage.setVisibility(View.VISIBLE);
            cycleTitle.setVisibility(View.INVISIBLE);
            exerciseTitle.setVisibility(View.INVISIBLE);
            exerciseDuration.setVisibility(View.INVISIBLE);
            exerciseDescription.setVisibility(View.INVISIBLE);
            displayTime.setVisibility(View.INVISIBLE);
            readyButton.setVisibility(View.INVISIBLE);
            divider.setVisibility(View.INVISIBLE);
            finished=true;
        }

        exerciseTitle.setText(currentExercise.getTitle());
        exerciseDuration.setText(currentExercise.getReps());
        displayTime.setText(currentExercise.getReps());
        exerciseDescription.setText(currentExercise.getDetail());
    }
}