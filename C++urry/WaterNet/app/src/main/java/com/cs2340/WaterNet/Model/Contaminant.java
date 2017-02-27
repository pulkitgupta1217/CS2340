package com.cs2340.WaterNet.Model;

/**
 * Created by Pulkit Gupta on 2/27/2017.
 */

public enum Contaminant {
    CONTAMINANT("Contaminant", 0);
    String value;
    long ppm;
    Contaminant(String value, int ppm) {
        this.value = value;
        this.ppm = ppm;
    }
    void setPPM(long ppm) {
        this.ppm = ppm;
    }
    long getPPM() {
        return ppm;
    }
    String getValue() {
        return value;
    }
}
