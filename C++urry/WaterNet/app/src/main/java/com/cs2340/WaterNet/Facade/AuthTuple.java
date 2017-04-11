package com.cs2340.WaterNet.Facade;

import com.cs2340.WaterNet.Model.User;

/**
 * Created by pulki on 3/30/2017.
 */

public class AuthTuple {
    private boolean success = false;
    private String errorMessage = null;
    private User u;


    public AuthTuple(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }


    public boolean getSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public User getUser() {
        return u;
    }

    public void setUser(User u) {
        this.u = u;
    }


}
