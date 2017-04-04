package com.cs2340.WaterNet.Model;

import android.content.Intent;

/**
 * Created by pulki on 3/30/2017.
 */

public class LoginNTuple {
    private boolean success = false;
    private String errorMessage = "";
    private boolean finished = false;

    public LoginNTuple() {
        this(false, null);
    }

    public LoginNTuple(boolean success, String errorMessage) {
        this(success, errorMessage, false);
    }

    public LoginNTuple(boolean success, String errorMessage, boolean finished) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
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
    /*
    public Intent getIntent() {
        return intent;
    }
    */
}
