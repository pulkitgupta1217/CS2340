package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cs2340.WaterNet.Model.Report;
import com.cs2340.WaterNet.Model.User;
import com.cs2340.WaterNet.Model.WaterCondition;
import com.cs2340.WaterNet.Model.WaterType;
import com.cs2340.WaterNet.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView btnViewProfile, signOut, gotoCreateReportBtn;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Singleton.setInstance(Firebase.getSingleton());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
                i.putExtra("user", getIntent().getSerializableExtra("user"));
                startActivity(i);
                finish();
            }
        });

        gotoCreateReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ReportActivity.class);
                i.putExtra("user", getIntent().getSerializableExtra("user"));
                startActivity(i);
                finish();
            }
        });

        RecyclerView recycler = (RecyclerView) findViewById(R.id.ReportRecyclerView);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        recycler.setAdapter(
                new FirebaseRecyclerAdapter<Report, ReportHolder>(Report.class, android.R.layout.two_line_list_item, ReportHolder.class, database.getReference().child("reports")) {
                    @Override
                    public void populateViewHolder(ReportHolder reportViewHolder, Report report, int position) {
                        reportViewHolder.setWaterConditionTV(report.getWaterCondition().toString());
                        reportViewHolder.setWaterTypeTV(report.getWaterType().toString());
                    }
                }
        );

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


    public static class ReportHolder extends RecyclerView.ViewHolder {
        private final TextView waterTypeTV;
        private final TextView waterConditionTV;

        public ReportHolder(View itemView) {
            super(itemView);
            waterTypeTV = (TextView) itemView.findViewById(android.R.id.text1);
            waterConditionTV = (TextView) itemView.findViewById(android.R.id.text2);
        }

        public void setWaterTypeTV(String name) {
            waterTypeTV.setText(name);
        }

        public void setWaterConditionTV(String text) {
            waterConditionTV.setText(text);
        }
    }

}