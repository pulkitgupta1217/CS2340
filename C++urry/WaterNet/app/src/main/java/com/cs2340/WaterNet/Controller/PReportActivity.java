package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs2340.WaterNet.Model.Contaminant;
import com.cs2340.WaterNet.Model.OverallCondition;
import com.cs2340.WaterNet.Model.PurityReport;
import com.cs2340.WaterNet.Model.User;
import com.cs2340.WaterNet.Model.Virus;
import com.cs2340.WaterNet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PReportActivity extends AppCompatActivity {

    private Button create, cancel;

    private EditText latField, longField;
    private Spinner virusSpinner, contaminantSpinner, overallConditionSpinner;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

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
        database = FirebaseDatabase.getInstance();

        //get current user
        final FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final User user = (User) (getIntent().getSerializableExtra("user"));
//        Log.d("***", user.getUserID() + "");

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fuser = firebaseAuth.getCurrentUser();
                if (fuser == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Intent i = new Intent(PReportActivity.this, LoginActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                    finish();
                }
            }
        };

        create = (Button) findViewById(R.id.push_report_button);
        cancel = (Button) findViewById(R.id.cancel_report_button);

        latField = (EditText) findViewById(R.id.latitude_input);
        longField = (EditText) findViewById(R.id.longitude_input);

        virusSpinner = (Spinner) findViewById(R.id.virusSpinner);
        ArrayAdapter<String> virusSpinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Virus.values());
        virusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        virusSpinner.setAdapter(virusSpinnerAdapter);

        contaminantSpinner = (Spinner) findViewById(R.id.contaminantSpinner);
        ArrayAdapter<String> conditionTypeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Contaminant.values());
        conditionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contaminantSpinner.setAdapter(conditionTypeAdapter);

        overallConditionSpinner = (Spinner) findViewById(R.id.oConditionSpinner);
        ArrayAdapter<String> overallConditionSpinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, OverallCondition.values());
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
                i.putExtra("user", user);
                startActivity(i);
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String latstring = latField.getText().toString().trim();
                String longstring = longField.getText().toString().trim();

                if (TextUtils.isEmpty(latstring)) {
                    Toast.makeText(getApplicationContext(), "Enter latitude!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(longstring)) {
                    Toast.makeText(getApplicationContext(), "Enter longtitude!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int latitude = Integer.parseInt(latstring);
                int longitude = Integer.parseInt(longstring);
                Virus v = (Virus) (virusSpinner.getSelectedItem());
                Contaminant c = (Contaminant) (contaminantSpinner.getSelectedItem());
                OverallCondition oc = (OverallCondition) (overallConditionSpinner.getSelectedItem());

                writeNewPost(user, latitude, longitude, v, oc, c);
                Intent i = new Intent(PReportActivity.this, MainActivity.class);
                i.putExtra("user", user);
                startActivity(i);
                finish();

            }
        });

    }

    private void writeNewPost(User u, double lat, double lng, Virus v, OverallCondition oc, Contaminant c) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts mmvm/$postid simultaneously
        PurityReport post = new PurityReport(u.getUsername(), lat, lng, v, c, oc);
        database.getReference().child("purity_reports").push().setValue(post);

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