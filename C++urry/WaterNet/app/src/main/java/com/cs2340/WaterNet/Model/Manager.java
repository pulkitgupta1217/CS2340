package com.cs2340.WaterNet.Model;

/**
 * Created by rajatkhanna on 2/20/17.
 */

public class Manager extends User {

    public Manager(String username, String email) {
        super(username, email);
        userType = UserType.MANAGER;
    }
}
