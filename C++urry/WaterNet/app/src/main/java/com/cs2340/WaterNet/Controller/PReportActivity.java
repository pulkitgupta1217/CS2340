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
import com.cs2340.WaterNet.Model.OverallCondition;
import com.cs2340.WaterNet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PReportActivity extends AppCompatActivity {



    private EditText latField, longField, cPPMField, vPPMField;
    private Spinner overallConditionSpinner;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Singleton.setInstance(Firebase.getSingleton());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preport);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user


//        Log.d("***", user.getUserID() + "");

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebase_user = firebaseAuth.getCurrentUser();
                if (firebase_user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Intent i = new Intent(PReportActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        private Button create = (Button) findViewById(R.id.push_report_button);
        private Button cancel = (Button) findViewById(R.id.cancel_report_button);

        latField = (EditText) findViewById(R.id.latitude_input);
        longField = (EditText) findViewById(R.id.longitude_input);
        vPPMField = (EditText) findViewById(R.id.virus_ppm_input);
        cPPMField = (EditText) findViewById(R.id.contaminant_ppm_input);

        overallConditionSpinner = (Spinner) findViewById(R.id.oConditionSpinner);
        ArrayAdapter<OverallCondition> overallConditionSpinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, OverallCondition.values());
        overallConditionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        overallConditionSpinner.setAdapter(overallConditionSpinnerAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PReportActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Facade.createPurityReport(latField.getText().toString().trim(), longField.getText().toString().trim(),
                        vPPMField.getText().toString().trim(), cPPMField.getText().toString().trim(),
                        (OverallCondition) overallConditionSpinner.getSelectedItem(), new Consumer<String>() {
                            public void accept(String s) {
                                if (s.length() != 0) {
                                    Intent i = new Intent(PReportActivity.this, MainActivity.class);
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