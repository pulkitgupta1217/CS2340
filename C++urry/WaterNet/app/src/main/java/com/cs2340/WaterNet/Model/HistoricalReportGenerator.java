package com.cs2340.WaterNet.Model;

import java.util.List;

/**
 * Created by Pulkit Gupta on 2/28/2017.
 */

public class HistoricalReportGenerator {
    //Graph graph;
    List<Pin> pins;
    public HistoricalReportGenerator(List<Pin> pins) {
        this.pins = pins;
    }
    public /*Graph*/ void generateHistoricalReport() {
        /*return */generateHistoricalReport(pins);
    }
    public static /*Graph*/ void generateHistoricalReport(List<Pin> data) {
        //some stuff
    }
}
