package com.cs2340.WaterNet.Model;

/**
 * Created by pulki on 2/26/2017.
 */

public enum WaterCondition {
    WASTE ("Waste"),
    TREATABLECLEAR("Treatable-Clear"),
    TREATABLEMUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private String value;
    WaterCondition(String value) {
        this.value = value;
    }
}
