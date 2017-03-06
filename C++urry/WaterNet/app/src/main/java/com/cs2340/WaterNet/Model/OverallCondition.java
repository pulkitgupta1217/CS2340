package com.cs2340.WaterNet.Model;

/**
 * an enum that has the possible overall conditions of a source and associates
 * them to a string representation
 * Created by Pulkit Gupta on 2/27/2017.
 */

public enum OverallCondition {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    String value;

    /**
     * creates a overallcondition
     * @param value the string value of the overall condition
     */
    OverallCondition(String value) {
        this.value = value;
    }

    /**
     * gets the string value of this enum
     * @return value
     */
    public String getValue() {
        return value;
    }
}
