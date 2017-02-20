package com.cs2340.WaterNet.Model;

/**
 * Created by rajatkhanna on 2/20/17.
 */

public enum UserType {
    USER("USR"),
    WORKER("WRK"),
    MANAGER("MNG"),
    ADMIN("ADM");

    private String value;
    private UserType(String value) {
        this.value = value;
    }
}
