package com.cs2340.WaterNet.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pulkit Gupta on 2/28/2017.
 */

public class Facade {
    /**
     * goes between controller and model objects, contains the current user and is populated with the pin list,
     * updated on signin and when necessary
     * basically the ultimate information holder and interfacer.
     * activities only communicate with this and this communicates with the rest of model and
     * returns information to the activity
     */
    private static User currUser;
    private static List<Pin> pinList;
    private static List<User> userList;

    private Facade() {
        pinList = new LinkedList<Pin>();
        userList = new LinkedList<User>();
    }

    public static User getCurrUser() {
        return currUser;
    }

    public static void setCurrUser(User currUser) { //may use firebase user instead and generate user based on that
        Facade.currUser = currUser;
    }

    public static List<Pin> getPinList() {
        return pinList;
    }

    public static void setPinList() {
        //firebase grab pins from db
    }

    public static List<User> getUserList() {
        return userList;
    }

    public static void setUserList() {
        //firebase grab users from db and filter out admins etc.
    }
}
