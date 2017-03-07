package com.cs2340.WaterNet.Model;

/**
 * Created by Pulkit Gupta on 2/27/2017.
 */

public enum Virus {

    VIRUS("Contaminant", 0);
    String value;
    long ppm;

    /**
     * constructor to set the values of the enum
     * @param value the string value of the virus
     * @param ppm the ppm of the virus in the water
     */
    Virus(String value, long ppm) {
        this.value = value;
        this.ppm = ppm;
    }

    /**
     * set the ppm of this virus
     * @param ppm the new ppm
     */
    void setPPM(long ppm) {
        this.ppm = ppm;
    }

    /**
     * get the ppm of this virus
     * @return the ppm
     */
    long getPPM() {
        return ppm;
    }

    /**
     * get the string form of the virus
     * @return the string value of the virus
     */
    String getValue() {
        return value;
    }
}
