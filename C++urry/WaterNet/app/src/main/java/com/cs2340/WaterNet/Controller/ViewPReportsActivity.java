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

import com.cs2340.WaterNet.Model.PurityReport;
import com.cs2340.WaterNet.Facade.PurityReportHolder;
import com.cs2340.WaterNet.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ViewPReportsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_preport);

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
                    startActivity(new Intent(ViewPReportsActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        recycler = (RecyclerView) findViewById(R.id.PReportRecyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        //TODO: move to Facade
        recycler.setAdapter(
                new FirebaseRecyclerAdapter<PurityReport, PurityReportHolder>(PurityReport.class, R.layout.preport_item_layout, PurityReportHolder.class, database.getReference().child("purity_reports")) {
                    @Override
                    public void populateViewHolder(PurityReportHolder preportViewHolder, PurityReport preport, int position) {
                        preportViewHolder.setOverallConditionTV(preport.getOverallCondition().toString());
                        preportViewHolder.setInfoTV(preport.getCreator() + "  " + preport.getDateTime());
                        preportViewHolder.setLocationTV(preport.getSite().toString());
                        preportViewHolder.setContaminantppmTV("Contaminant PPM: " + preport.getContaminant().getPPM());
                        preportViewHolder.setVirusppmTV("Virus PPM: " + preport.getVirus().getPPM());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}