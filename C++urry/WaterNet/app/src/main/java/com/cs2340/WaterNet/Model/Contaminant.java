package com.cs2340.WaterNet.Model;

/**
 * an enum for the different contaminants that could be present in the water
 * it also contains a string form of the contaminant and the ppm of each contaminant,
 * it also has a general contaminant that stores the ppm if there are many contaminants
 * Created by Pulkit Gupta on 2/27/2017.
 */

public enum Contaminant {
    CONTAMINANT("Contaminant", 0);

    String value;
    long ppm;

    /**
     * creates a Contaminant with a name and a ppm
     * @param value name of contaminant
     * @param ppm ppm of contaminant
     */
    Contaminant(String value, int ppm) {
        this.value = value;
        this.ppm = ppm;
    }

    /**
     * sets ppm
     * @param ppm new ppm of contaminant
     */
    void setPPM(long ppm) {
        this.ppm = ppm;
    }

    /**
     * returns ppm
     * @return ppm
     */
    long getPPM() {
        return ppm;
    }

    /**
     * returns string form of contaminant
     * @return value
     */
    String getValue() {
        return value;
    }
}
