package com.example.qfit_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.qfit_app.R;
import com.example.qfit_app.ui.ui.signup.SignUpFragment;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SignUpFragment.newInstance())
                    .commitNow();
        }
    }
}