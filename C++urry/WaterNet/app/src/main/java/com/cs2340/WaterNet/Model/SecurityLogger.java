package com.cs2340.WaterNet.Model;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Rajat Khanna on 2/23/17.
 */

public class SecurityLogger {

    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    /**
     * writes new security log entry to firebase
     * @param message the new security log entry
     */
    public static void writeNewSecurityLog(String message) {
        firebaseDatabase.getReference().child("securityLog").push().setValue(message);
        Log.d("***", message + " pushed to securityLog");
    }

}
