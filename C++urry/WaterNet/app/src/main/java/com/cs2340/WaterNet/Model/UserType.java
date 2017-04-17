package com.cs2340.WaterNet.Model;

import java.io.Serializable;

/**
 * Created by Rajat Khanna on 2/20/17.
 */

public enum UserType implements Serializable {
    USER("USR"),
    WORKER("WRK"),
    MANAGER("MNG"),
    ADMIN("ADM");

    private final String value;
    UserType(String value) {
        this.value = value;
    }
}
