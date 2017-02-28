package com.cs2340.WaterNet.Model;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Pulkit Gupta on 2/26/2017.
 */

public class Singleton implements Serializable{
    private static Singleton instance = null;


    public long userID;
    private long reportID;
    private long purityReportID;
    private SimpleDateFormat dateTime;
    private Calendar c;
    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public Singleton(){
        userID = 1;
        reportID = 1;
        purityReportID = 1;
        c = Calendar.getInstance();
        dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTime.format(c.getTime());
    }

    public static Singleton getInstance(){
        if(instance == null)
        {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                    Log.d("***", "made new Singleton");
                }
            }
        }
        return instance;
    }
    public static void setInstance(Singleton fromdb) {
        instance = fromdb;
    }

    public boolean hasInstance(){
        return instance != null;
    }

    public long getUserID() {
        //userID++;
        pushToDatabase();
        return userID++;
    }
    public long getReportID() {
        //reportID++;
        pushToDatabase();
        return reportID++;
    }
    public long getPurityReportID() {
        //purityReportID++;
        pushToDatabase();
        return purityReportID++;
    }
    public String getTime() {
        return dateTime.format(c.getTime());
    }

    private void pushToDatabase() {
        //firebaseDatabase.getInstance().getReference().child("Singleton").setValue(Singleton.getInstance());
    }
}
