package com.example.qfit_app.ui.ui.login;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qfit_app.MainActivity;
import com.example.qfit_app.R;
import com.example.qfit_app.api.ApiClient;

public class LoginActivity extends AppCompatActivity {

    ApiClient apiClient;

    private LoginViewModel loginViewModel;
    EditText usernameEditText;
    EditText passwordEditText;

    private int signupStep=1;

    private static LoginActivity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            apiClient.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());

            if(apiClient.token != "emptys" && apiClient.token!="invalid") {
                Intent login = new Intent(getApplicationContext(), MainActivity.class);
                login.putExtra("username", usernameEditText.getText());
                login.putExtra("password", passwordEditText.getText());
                Intent lastIntent = getIntent();
                if(lastIntent.getExtras()!=null && lastIntent.getExtras().get("sharedRoutine")!=null)
                    login.putExtra("sharedRoutine", lastIntent.getExtras().get("sharedRoutine").toString());
                startActivity(login);
            }
        });

        signupButton.setOnClickListener(v->{
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
        });


    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + usernameEditText.getText();//model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        try {

            apiClient.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());

            Intent login = new Intent(getApplicationContext(), MainActivity.class);
            login.putExtra("username", usernameEditText.getText());
            login.putExtra("password", passwordEditText.getText());
    //        login.putExtra("apiClient", apiClient);
    //        login.putExtra("token", apiClient.getToken());
            startActivity(login);
        } catch(Exception e)  {
           Log.d("logg", "its maginc");
        }

//        Intent login = new Intent(getApplicationContext(), MainActivity.class);
//
//        login.putExtra("username", usernameEditText.getText());
//        login.putExtra("password", passwordEditText.getText());
////        login.putExtra("apiClient", apiClient);
//        login.putExtra("token", apiClient.getToken());
//        startActivity(login);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}