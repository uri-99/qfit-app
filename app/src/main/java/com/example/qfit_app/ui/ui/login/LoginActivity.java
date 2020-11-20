package com.example.qfit_app.ui.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qfit_app.Exercise;
import com.example.qfit_app.MainActivity;
import com.example.qfit_app.MyApplication;
import com.example.qfit_app.MyPreferences;
import com.example.qfit_app.R;
import com.example.qfit_app.api.ApiClient;
import com.example.qfit_app.repository.UserRepository;
import com.example.qfit_app.routine_in_progress;

import java.io.Serializable;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    ApiClient apiClient;
    MyApplication application;
    private LoginViewModel loginViewModel;
    EditText usernameEditText;
    EditText passwordEditText;
    private UserRepository userRepository;

    private int signupStep=1;

    private static LoginActivity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (MyApplication) this.getApplication();
        userRepository = application.getUserRepository();
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button signupButton = findViewById(R.id.sign_up);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final EditText emailEditText = findViewById(R.id.email);
        Button verifyCodeButton = findViewById(R.id.verifyCode);
        EditText confirmationCode = findViewById(R.id.confirmationCode);
        ImageButton backToLogin = findViewById(R.id.backToLogin);

        context=this;

        apiClient = new ApiClient();
        apiClient.setContext(context);

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.VISIBLE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            finish();
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            userRepository
                    .login(usernameEditText.getText().toString(), passwordEditText.getText().toString())
                    .observe(this, resource -> {
                        switch (resource.status) {
                            case LOADING:
                                loginButton.setEnabled(false);
                                loadingProgressBar.setVisibility(View.VISIBLE);
                                break;
                            case SUCCESS:
                                loginButton.setEnabled(true);
                                application.getPreferences().setAuthToken(resource.data);
                                Toast.makeText(application, getString(R.string.operation_success), Toast.LENGTH_SHORT).show();
                                Intent login = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(login);
                                finish();
                                break;
                            case ERROR:
                                loginButton.setEnabled(true);
                                loadingProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(application, resource.message, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    });

        });

        /*signupButton.setOnClickListener(v->{
            if(signupStep==1){
                emailEditText.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.GONE);
                signupButton.setBackgroundColor(Color.rgb(1,225,64));
                Log.d("logg", String.format("%d",signupStep));
                signupStep++;
                Log.d("logg", String.format("%d",signupStep));
                backToLogin.setVisibility(View.VISIBLE);
            } else if(signupStep==2) {
                verifyCodeButton.setVisibility(View.VISIBLE);
                confirmationCode.setVisibility(View.VISIBLE);
                Toast.makeText(context, R.string.sentCode, Toast.LENGTH_SHORT).show();
                apiClient.createUser(usernameEditText.getText().toString(), passwordEditText.getText().toString(), emailEditText.getText().toString());
                Log.d("logg", String.format("%d",signupStep));
                apiClient.setContext(context);
            }
        });

        verifyCodeButton.setOnClickListener(v->{
            apiClient.verifyEmail(emailEditText.getText().toString(), confirmationCode.getText().toString());
        });

        backToLogin.setOnClickListener(v->{
            signupStep=1;
            emailEditText.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            verifyCodeButton.setVisibility(View.GONE);
            confirmationCode.setVisibility(View.GONE);
            backToLogin.setVisibility(View.GONE);
        });*/


    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + usernameEditText.getText();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}