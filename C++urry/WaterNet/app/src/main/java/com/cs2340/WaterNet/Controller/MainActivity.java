package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.Model.UserType;
import com.cs2340.WaterNet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * main activity/landing page
 */
public class MainActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        TextView btnViewProfile, signOut, gotoCreateReportBtn, viewMapBtn,
                gotoCreatePurityReportBtn;
        Button viewGraphBtn, viewPurityReports, adminMenuBtn;
        RecyclerView recycler;

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        signOut = (TextView) findViewById(R.id.sign_out);
        btnViewProfile = (TextView) findViewById(R.id.view_profile);
        gotoCreateReportBtn = (TextView) findViewById(R.id.create_report_btn);
        gotoCreatePurityReportBtn = (TextView) findViewById(R.id.create_purity_report_btn);
        viewMapBtn = (TextView) findViewById(R.id.view_map);
        viewPurityReports = (Button) findViewById(R.id.view_purity_reports_button);
        viewGraphBtn = (Button) findViewById(R.id.view_graph);
        adminMenuBtn = (Button) findViewById(R.id.admin_menu_button);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Facade.signOut();
            }
        });

        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

        gotoCreateReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(i);
                finish();
            }
        });

        gotoCreatePurityReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PReportActivity.class);
                startActivity(i);
                finish();
            }
        });

        viewMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
                finish();
            }
        });

        adminMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(i);
                finish();
            }
        });


        viewPurityReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ViewPReportsActivity.class);
                startActivity(i);
                finish();
            }
        });

        viewGraphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(i);
                finish();
            }
        });

        //TODO: should this be user?
        UserType currType = Facade.getCurrUser().getUserType();
        if (!(currType == UserType.MANAGER || currType == UserType.ADMIN)) {
            viewPurityReports.setVisibility(View.GONE);
            viewGraphBtn.setVisibility(View.GONE);
        }

        if (Facade.getCurrUser().getUserType() != UserType.ADMIN) {
            adminMenuBtn.setVisibility(View.GONE);
        }

        if (Facade.getCurrUser().getUserType() == UserType.USER) {
            gotoCreatePurityReportBtn.setVisibility(View.GONE);
        }

        recycler = (RecyclerView) findViewById(R.id.ReportRecyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        recycler.setAdapter( Facade.createReportAdapter(R.layout.report_item_layout));

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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Facade.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }




}