package com.cs2340.WaterNet.Model;

/**
 * the manager class, a manager has all of the rights and abilities of a Worker in
 * addition to viewing historical reports, view trends, delete reports
 * Created by Rajat Khanna on 2/20/17.
 */

public class Manager extends Worker {

    /**
     * creates a manager as a user and then sets it as a Manager
     * @param username the manager's username
     * @param email the manager's email
     */
    public Manager(String username, String email) {
        super(username, email);
        userType = UserType.MANAGER;
    }
}
