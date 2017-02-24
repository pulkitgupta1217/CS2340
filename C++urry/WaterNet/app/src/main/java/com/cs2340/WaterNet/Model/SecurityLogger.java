package com.cs2340.WaterNet.Model;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rajatkhanna on 2/23/17.
 */

public class SecurityLogger {

    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public static void writeNewSecurityLog(String message) {
        firebaseDatabase.getInstance().getReference().child("securityLog").push().setValue(message);
        Log.d("***", message + " pushed to securityLog");
    }

}
