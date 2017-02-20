package com.cs2340.WaterNet.Model;

/**
 * Created by rajatkhanna on 2/18/17.
 */

public class User {

    public String username, email;
    private static int userCount;
    public int userId;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        userCount++;
        userId = userCount;
    }

    public String toString() {
        return username + " " + email;
    }

}
