package com.cs2340.WaterNet.Model;

import android.support.design.widget.CoordinatorLayout;

import java.io.Serializable;

/**
 * Created by Rajat Khanna on 2/18/17.
 * Edited by Pulkit Gupta on 2/25/17.
 */

public class User implements Serializable{

    private String username, email, name = "", address = "", phone = "";
    //private static int userCount;
    private Long userID;
    private Boolean banned;
    UserType userType = UserType.USER;


    /**
     * default constructor required for firebase
     */
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    /**
     * creates a user using these parameters sets some default values using the singleton
     * @param username the user's username
     * @param email the user's email
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        //userCount++;
        //userId = userCount;
        name = "no name";
        address = "no address";
        phone = "###-###-####";
        userID = Singleton.getInstance().getUserID();
        banned = false;
    }

    @Override
    public String toString() {
        return username + " " + email;
    }

    /**
     * returns the user's type so that certain menus can be restricted
     * @return userType
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * get the username of the user
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * get the email of the user that is also used for authentication
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * get the userID of the user
     * @return userID
     */
    public long getUserID() {
        return userID;
    }

    /**
     * set a new email for the user
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get the address of the user
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * set the new address of the user
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get the phone number of the user
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * set the phone number of the user
     * @param phone the new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * get the name of the user
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set a new name for the user
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the userType of the user
     * @param userType the new userType
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * check the ban status of the user
     * @return true if the user is banned, false otherwise
     */
    public boolean isBanned() {
        return banned.booleanValue();
    }

    /**
     * reassign the banned status of the user
     * @param newStatus the new ban status of the user
     */
    public void setBanned(boolean newStatus) {
        this.banned = newStatus;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        try {
            User other = (User) o;
            if (other.getUsername().equals(username) && other.getEmail().equals(email)) {
                return true;
            } else {
                return false;
            }
        } catch (ClassCastException c) {
            return false;
        }
    }
}
