package com.cs2340.WaterNet.Model;

/**
 * the admin class, an admin has all the rights and abilities of a manager in addition to
 * deleting accounts, ban users from submitting reports, can unblock locked accounts,
 * and view the security log
 * Created by Rajat Khanna on 2/20/17.
 */

public class Admin extends Manager {

    /**
     * creates an admin as a user and then sets it as an Admin
     * @param username username of Admin
     * @param email email of Admin
     */
    public Admin(String username, String email) {
        super(username, email);
        userType = UserType.ADMIN;
    }
}
