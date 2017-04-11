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
import com.cs2340.WaterNet.Model.Report;
import com.cs2340.WaterNet.Model.UserType;
import com.cs2340.WaterNet.Facade.ReportHolder;
import com.cs2340.WaterNet.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        TextView btnViewProfile, signOut, gotoCreateReportBtn, viewMapBtn, gotoCreatePurityReportBtn, viewpReports, viewGraphBtn;
        FirebaseDatabase database;
        RecyclerView recycler;

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



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
        gotoCreatePurityReportBtn = (TextView) findViewById(R.id.create_preport_btn);
        viewMapBtn = (TextView) findViewById(R.id.view_map);
        viewpReports = (Button) findViewById(R.id.view_purity_reports_button);
        viewGraphBtn = (Button) findViewById(R.id.view_graph);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
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


        viewpReports.setOnClickListener(new View.OnClickListener() {
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
        if (Facade.getCurrUser().getUserType() != UserType.MANAGER) {
            viewpReports.setVisibility(View.INVISIBLE);
            viewGraphBtn.setVisibility(View.INVISIBLE);
        }

        if (Facade.getCurrUser().getUserType() == UserType.USER)
            gotoCreatePurityReportBtn.setVisibility(View.INVISIBLE);

        recycler = (RecyclerView) findViewById(R.id.ReportRecyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        recycler.setAdapter(
                new FirebaseRecyclerAdapter<Report, ReportHolder>(Report.class, R.layout.report_item_layout, ReportHolder.class, database.getReference().child("reports")) {
                    @Override
                    public void populateViewHolder(ReportHolder reportViewHolder, Report report, int position) {
                        reportViewHolder.setWaterConditionTV(report.getWaterCondition().toString());
                        reportViewHolder.setWaterTypeTV(report.getWaterType().toString());
                        reportViewHolder.setInfoTV(report.getCreator() + "  " + report.getDateTime());
                        reportViewHolder.setLocationTV(report.getSite().toString());
                    }
                }
        );

    }

    //sign out method
    private void signOut() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




}