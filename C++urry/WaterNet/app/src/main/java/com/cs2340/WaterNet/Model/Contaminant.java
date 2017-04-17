package com.cs2340.WaterNet.Model;

/**
 * Created by Pulkit Gupta on 2/27/2017.
 */

public class Contaminant {

    private long ppm;

    /**
     * empty constructor for recycler view
     */
    public Contaminant() {
        ppm = 0;
    }

    /**
     * constructor to set the values of the object
     * @param ppm the ppm of the virus in the water
     */
    public Contaminant(long ppm) {
        this.ppm = ppm;
    }

    /**
     * set the ppm of this contaminant
     * @param ppm the new ppm
     */
    public void setPPM(long ppm) {
        this.ppm = ppm;
    }

    /**
     * get the ppm of this contaminant
     * @return the ppm
     */
    public long getPPM() {
        return ppm;
    }

}
