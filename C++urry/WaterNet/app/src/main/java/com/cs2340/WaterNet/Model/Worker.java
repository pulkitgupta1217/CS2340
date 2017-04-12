package com.cs2340.WaterNet.Model;

/**
 * the Worker class, a worker has all of the right and abilities of a User, in addition to the
 * ability to report on water purity levels
 * Created by Pulkit Gupta on 2/20/17.
 */

public class Worker extends User {

    /**
     * the constructor for the worker
     * @param username the worker's username
     * @param email the worker's email
     */
    public Worker(String username, String email) {
        super(username, email);
        userType = UserType.WORKER;
    }
}
