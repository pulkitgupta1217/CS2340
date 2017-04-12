package com.cs2340.WaterNet.Facade;

import com.cs2340.WaterNet.Model.User;

/**
 * Tuple that stores auth information
 * Created by Pulkit Gupta on 3/30/2017.
 */

public class AuthTuple {
    private boolean success = false;
    private String errorMessage = null;
    private User u;


    /**
     * constructor
     * @param success whether or not a user was authenticated
     * @param errorMessage the message associated with this authentication attempt
     */
    public AuthTuple(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }


    /**
     * get the success state of the tuple
     * @return the success state
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * get the error message
     * @return error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * set the success state
     * @param success new state
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * set the error message
     * @param errorMessage new error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    /**
     * get the user in db from this auth attempt
     * @return
     */
    public User getUser() {
        return u;
    }

    /**
     * set the user of this auth attempt
     * @param u
     */
    public void setUser(User u) {
        this.u = u;
    }


}
