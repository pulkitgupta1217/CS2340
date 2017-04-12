package com.cs2340.WaterNet.Model;

/**
 * Created by Pulkit Gupta on 2/26/2017.
 */

public enum WaterType {
    BOTTLED ("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private final String value;

    /**
     * constructor to make the enum using the string
     * @param value the string form of the enum
     */
    WaterType(String value) {
        this.value = value;
    }
}
