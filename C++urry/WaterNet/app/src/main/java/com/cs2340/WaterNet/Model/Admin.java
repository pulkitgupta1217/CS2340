package com.cs2340.WaterNet.Model;

/**
 * Created by rajatkhanna on 2/20/17.
 */

public class Admin extends User {

    public Admin(String username, String email) {
        super(username, email);
        userType = UserType.ADMIN;
    }
}
