package com.cs2340.WaterNet.Model;

/**
 * Created by pulki on 2/26/2017.
 */

public enum WaterType {
    BOTTLED ("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private String value;
    WaterType(String value) {
        this.value = value;
    }
}
