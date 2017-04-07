package com.cs2340.WaterNet.Model;


/**
 * Created by pulki on 3/30/2017.
 */

public class AuthTuple {
    private boolean success = false;
    private String errorMessage = "";

    public AuthTuple() {
        this(false, null);
    }

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


}
