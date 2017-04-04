package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cs2340.WaterNet.Model.Facade;
import com.cs2340.WaterNet.Model.LoginNTuple;
import com.cs2340.WaterNet.Model.Manager;
import com.cs2340.WaterNet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    public static Button btnSignup, btnLogin, btnReset;
    private FirebaseDatabase database;

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public EditText getInputEmail() {
        return inputEmail;
    }

    public EditText getInputPassword() {
        return inputPassword;
    }

    public Button getBtnSignup() {
        return btnSignup;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }

    public Button getBtnReset() {
        return btnReset;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

        // set the view now
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Activity: ", Thread.currentThread().getName());
                Log.d("Starting", "starting");
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                Log.d("ACTIVITY: ", "begining Facade");
                LoginNTuple authenticated = Facade.validateLogin(email, password, progressBar);
                Log.d("ACTIVITY: ", "exited FACADE");
                /*while (!authenticated.isFinished()) {
                    //Log.d("AUTH: ", "authenticating");
                }*/
                Log.d("Activity: ", "finished authentication");
                //TODO: MAKE THIS WAIT UNTIL FACADE FINISHES
                String error = authenticated.getErrorMessage();
                Log.d("Error", error);
                if (error.length() != 0) {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
                if (authenticated.getSuccess()) {
                    Log.d("***", "moving to main activity");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //startActivity(authenticated.getIntent());
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
