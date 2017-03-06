package com.cs2340.WaterNet.Model;

import java.util.List;

/**
 * creates a google Map object with pins placed based on the constructor or a list of pins
 * passed into it
 * Created by Pulkit Gupta on 2/28/2017.
 */

public class MapGenerator {
    //GoogleMap map
    private static List<Pin> pins;
    /**
     * creates a MapGenerator object that has a list of pins
     * @param pins the list of all pins that the map will hold
     */
    public MapGenerator(List<Pin> pins) {
        this.pins = pins;
        //stuff to make a map and load it up with pins
    }

    /**
     * creates a map that has a singular movable pin that would be the location of a new report
     */
    public MapGenerator() {
        //generates empty map with movable pin to add a report
    }

    /**
     * creates the map
     * @return a map
     */
    public static /*Google map with pin*/ Object getMap() {
        //creates a map and returns it
        return null;
    }
    public static /*Google map with pin*/ Object getMap(List<Pin> pins) {
        //creates a map using pins and returns it
        return null;
    }
}
