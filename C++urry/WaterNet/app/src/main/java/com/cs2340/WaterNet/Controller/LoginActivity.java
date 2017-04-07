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

import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.Model.AuthTuple;
import com.cs2340.WaterNet.Model.Consumer;
import com.cs2340.WaterNet.R;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Facade.start();
        super.onCreate(savedInstanceState);

        Log.d("START", "STARTING APP");
        //Get Firebase auth instance

        // set the view now
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnSignup, btnLogin, btnReset;
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);


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

                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                Facade.validateLogin(email, password, progressBar, new Consumer<AuthTuple>() {
                    public void accept(AuthTuple tuple) {

                        String error = tuple.getErrorMessage();
                        Log.d("Error", error);
                        if (error.length() != 0) {
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                        if (tuple.getSuccess()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            //startActivity(authenticated.getIntent());
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        });
    }
}
