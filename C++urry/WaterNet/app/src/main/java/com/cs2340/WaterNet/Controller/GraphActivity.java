package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.cs2340.WaterNet.Model.PurityReport;
import com.cs2340.WaterNet.Model.Singleton;
import com.cs2340.WaterNet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;


public class GraphActivity extends AppCompatActivity {


    private FirebaseAuth.AuthStateListener authListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(GraphActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        final LineGraphSeries<DataPoint> vseries = new LineGraphSeries<>();
        final LineGraphSeries<DataPoint> cseries = new LineGraphSeries<>();

        graph.setTitle("PPM Over Time Graph");
        graph.getLegendRenderer().setVisible(true);
        vseries.setTitle("Virus");
        cseries.setTitle("Contaminant");
        cseries.setColor(Color.RED);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));

        ValueEventListener postListener = new ValueEventListener() {

            int index = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vseries.resetData(new DataPoint[]{});
                cseries.resetData(new DataPoint[]{});
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    index++;
                    PurityReport pr = ds.getValue(PurityReport.class);

                    try {
                        vseries.appendData(new DataPoint(Singleton.getInstance().getDateTimeFormat().parse(pr.getDateTime()), pr.getVirus().getPPM()),true, 100);
                        cseries.appendData(new DataPoint(Singleton.getInstance().getDateTimeFormat().parse(pr.getDateTime()), pr.getContaminant().getPPM()),true, 100);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                graph.removeAllSeries();
                graph.addSeries(vseries);
                graph.addSeries(cseries);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        FirebaseDatabase.getInstance().getReference().child("purity_reports").addValueEventListener(postListener);

    }


}
