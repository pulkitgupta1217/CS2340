package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs2340.WaterNet.Model.Consumer;
import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.Model.WaterCondition;
import com.cs2340.WaterNet.Model.WaterType;
import com.cs2340.WaterNet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ReportActivity extends AppCompatActivity {


    private EditText latField, longField;
    private Spinner waterTypeSpinner, conditionTypeSpinner;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @SuppressWarnings({"unchecked", "all"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button create, cancel;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fuser = firebaseAuth.getCurrentUser();
                if (fuser == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Intent i = new Intent(ReportActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        create = (Button) findViewById(R.id.push_report_button);
        cancel = (Button) findViewById(R.id.cancel_report_button);

        latField = (EditText) findViewById(R.id.latitude_input);
        longField = (EditText) findViewById(R.id.longitude_input);

        waterTypeSpinner = (Spinner) findViewById(R.id.waterTypeSpinner);
        ArrayAdapter<WaterType> waterTypeAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, WaterType.values());
        waterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(waterTypeAdapter);

        conditionTypeSpinner = (Spinner) findViewById(R.id.conditionTypeSpinner);
        ArrayAdapter<WaterCondition> conditionTypeAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, WaterCondition.values());
        conditionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionTypeSpinner.setAdapter(conditionTypeAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReportActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Facade.createReport(latField.getText().toString().trim(), longField.getText().toString().trim(),
                        (WaterType) waterTypeSpinner.getSelectedItem(), (WaterCondition) conditionTypeSpinner.getSelectedItem(), new Consumer<String>() {
                            public void accept(String s) {
                                if (s.equals("success!")) {
                                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ReportActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });

    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}