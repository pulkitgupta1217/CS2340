package com.cs2340.WaterNet.Controller;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cs2340.WaterNet.Model.PurityReport;
import com.cs2340.WaterNet.Model.Singleton;
import com.cs2340.WaterNet.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;


/**
 * graph activity
 */
public class GraphActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        /*//get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
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
        };*/

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        final LineGraphSeries<DataPoint> virus_series = new LineGraphSeries<>();
        final LineGraphSeries<DataPoint> containment_series = new LineGraphSeries<>();

        graph.setTitle("PPM Over Time Graph");
        graph.getLegendRenderer().setVisible(true);
        virus_series.setTitle("Virus");
        containment_series.setTitle("Contaminant");
        containment_series.setColor(Color.RED);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));

        ValueEventListener postListener = new ValueEventListener() {

            int index = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                virus_series.resetData(new DataPoint[]{});
                containment_series.resetData(new DataPoint[]{});
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    index++;
                    PurityReport pr = ds.getValue(PurityReport.class);

                    try {
                        virus_series.appendData(new DataPoint(
                                Singleton.getInstance().getDateTimeFormat().parse(pr.getDateTime()),
                                pr.getVirus().getPPM()),true, 100);
                        containment_series.appendData(new DataPoint(
                                Singleton.getInstance().getDateTimeFormat().parse(pr.getDateTime()),
                                pr.getContaminant().getPPM()),true, 100);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                graph.removeAllSeries();
                graph.addSeries(virus_series);
                graph.addSeries(containment_series);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        FirebaseDatabase.getInstance().getReference().child("purity_reports")
                .addValueEventListener(postListener);

    }


}
