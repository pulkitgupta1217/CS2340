package com.cs2340.WaterNet.Model;

import java.util.List;

/**
 * a historical report generator that can generate historical reports
 * based on either a set of pins of a set of reports
 * Created by Pulkit Gupta on 2/28/2017.
 */

class HistoricalReportGenerator {
    //Graph graph;
    private final List<Pin> pins;

    /**
     * creates a generator object preloaded with a set of relevant pins
     * @param pins the list of relevant pins to the location at which a graph was requested
     */
    public HistoricalReportGenerator(List<Pin> pins) {
        this.pins = pins;
    }

    /**
     * generates a historical report based on the pins that were loaded in the constructor
     * @return a graph/image that is the trend line
     */
    public /*Graph*/ Object generateHistoricalReport() {
        return generateHistoricalReportfromPins(pins);
    }

    /**
     * generates a map using a set of pins that is sent into it
     * @param data the set of pins from which to generate a report
     * @return a graph/image that is the trend line
     */
    private static /*Graph*/ Object generateHistoricalReportfromPins(List<Pin> data) {
        //some stuff
        return null;
    }

    /**
     * generates a graph using a list of reports
     * @param data the list of input reports
     * @return a graph object
     */
    public static /*Graph*/ Object generateHistoricalReport(List<PurityReport> data) {
        //some stuff
        return null;
    }
}
