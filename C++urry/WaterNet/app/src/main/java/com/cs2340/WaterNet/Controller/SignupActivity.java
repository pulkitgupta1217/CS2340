package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs2340.WaterNet.Model.Admin;
import com.cs2340.WaterNet.Model.AuthTuple;
import com.cs2340.WaterNet.Model.Consumer;
import com.cs2340.WaterNet.Model.Facade;
import com.cs2340.WaterNet.Model.Manager;
import com.cs2340.WaterNet.Model.SecurityLogger;
import com.cs2340.WaterNet.Model.Singleton;
import com.cs2340.WaterNet.Model.User;
import com.cs2340.WaterNet.Model.UserType;
import com.cs2340.WaterNet.Model.Worker;
import com.cs2340.WaterNet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputUsername;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private Spinner spinner;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //Get FirebaseDatabase
        database = FirebaseDatabase.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputUsername = (EditText) findViewById(R.id.username);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UserType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempEmail = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String username = inputUsername.getText().toString().trim();
                final UserType userType = (UserType) (spinner.getSelectedItem());

                Facade.createUser(tempEmail, username, password, userType, progressBar, new Consumer<AuthTuple>() {

                    public void accept(AuthTuple tuple) {
                        if (tuple.getErrorMessage().length() != 0) {
                            Toast.makeText(getApplicationContext(), tuple.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (tuple.getSuccess()) {
                            Intent i = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                });



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}