package com.cs2340.WaterNet.Model;

/**
 * Created by pulki on 2/26/2017.
 */

public enum WaterCondition {
    WASTE ("Waste"),
    TREATABLE_CLEAR("Treatable-Clear"),
    TREATABLE_MUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private final String value;

    /**
     * the constructor that will make a water condition based on the string
     * @param value the string form of the condition
     */
    WaterCondition(String value) {
        this.value = value;
    }
}
