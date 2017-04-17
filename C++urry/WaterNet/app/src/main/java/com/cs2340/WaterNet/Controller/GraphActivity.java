package com.cs2340.WaterNet.Controller;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.Model.PurityReport;
import com.cs2340.WaterNet.Model.Singleton;
import com.cs2340.WaterNet.R;
import com.google.firebase.database.FirebaseDatabase;
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

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        final LineGraphSeries<DataPoint> virus_series = new LineGraphSeries<>();
        final LineGraphSeries<DataPoint> containment_series = new LineGraphSeries<>();

        graph.setTitle("PPM Over Time Graph");
        graph.getLegendRenderer().setVisible(true);
        virus_series.setTitle("Virus");
        containment_series.setTitle("Contaminant");
        containment_series.setColor(Color.RED);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));


        Facade.setGraphListener(virus_series, containment_series, graph);

    }


}
