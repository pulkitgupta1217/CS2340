package com.cs2340.WaterNet.Model;

/**
 * Created by pulki on 2/27/2017.
 */

public enum OverallCondition {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    String value;

    OverallCondition(String value) {
        this.value = value;
    }
}
