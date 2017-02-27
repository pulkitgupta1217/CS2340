package com.cs2340.WaterNet.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.InterruptedIOException;
import java.io.Serializable;

/**
 * Created by rajatkhanna on 2/18/17.
 * Edited by Pulkit Gupta on 2/25/17.
 */

public class User implements Serializable{

    private String username, email, name = "", address = "", phone = "";
    //private static int userCount;
    private Long userID;
    private Boolean banned;
    UserType userType = UserType.USER;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

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

    public String toString() {
        return username + " " + email;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public long getUserID() {
        return userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isBanned() {
        return banned.booleanValue();
    }
    public void setBanned(boolean newStatus) {
        this.banned = Boolean.valueOf(newStatus);
    }

}
