package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2340.WaterNet.Model.Report;
import com.cs2340.WaterNet.Model.SecurityLogger;
import com.cs2340.WaterNet.Model.Singleton;
import com.cs2340.WaterNet.Model.User;
import com.cs2340.WaterNet.Model.UserType;
import com.cs2340.WaterNet.Model.WaterCondition;
import com.cs2340.WaterNet.Model.WaterType;
import com.cs2340.WaterNet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ReportActivity extends AppCompatActivity {

    private Button create, cancel;

    private EditText latField, longField;
    private Spinner waterTypeSpinner, conditionTypeSpinner;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Singleton.setInstance(Firebase.getSingleton());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

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
                    Intent i = new Intent(ReportActivity.this, LoginActivity.class);
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

        waterTypeSpinner = (Spinner) findViewById(R.id.waterTypeSpinner);
        ArrayAdapter<String> waterTypeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, WaterType.values());
        waterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        waterTypeSpinner.setAdapter(waterTypeAdapter);

        conditionTypeSpinner = (Spinner) findViewById(R.id.conditionTypeSpinner);
        ArrayAdapter<String> conditionTypeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, WaterCondition.values());
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
                WaterType wt = (WaterType) (waterTypeSpinner.getSelectedItem());
                WaterCondition wc = (WaterCondition) (conditionTypeSpinner.getSelectedItem());

                writeNewPost(user, latitude, longitude, wt, wc);
                Intent i = new Intent(ReportActivity.this, MainActivity.class);
                i.putExtra("user", user);
                startActivity(i);
                finish();

            }
        });

    }

    private void writeNewPost(User u, double lat, double lng, WaterType waterType, WaterCondition waterCondition) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts mmvm/$postid simultaneously
        Report post = new Report(u.getUsername(), lat, lng, waterType, waterCondition);
        database.getReference().child("reports").push().setValue(post);

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