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

    /**
     * the single run constructor that initializes the singleton instance that is stored within
     * the singleton
     */
    public Singleton(){
        userID = 1;
        reportID = 1;
        purityReportID = 1;
        c = Calendar.getInstance();
        dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTime.format(c.getTime());
    }

    /**
     * gets the instance of the singleton
     * @return the instance
     */
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

    /**
     * used to set the singleton from the database
     * @param fromdb the singleton stored in firebase
     */
    public static void setInstance(Singleton fromdb) {
        instance = fromdb;
    }

    /**
     * get an updated userid from the singleton
     * @return userid++
     */
    public long getUserID() {
        //userID++;
        pushToDatabase();
        return userID++;
    }

    /**
     * get an updated reportid from the singleton
     * @return reportid++
     */
    public long getReportID() {
        //reportID++;
        pushToDatabase();
        return reportID++;
    }

    /**
     * get an updated purityreportid from the singleton
     * @return purityreportid++
     */
    public long getPurityReportID() {
        //purityReportID++;
        pushToDatabase();
        return purityReportID++;
    }

    /**
     * return the datetime as a string
     * @return calendar.getTime as a string
     */
    public String getTime() {
        return dateTime.format(c.getTime());
    }

    public SimpleDateFormat getDateTimeFormat() {
        return dateTime;
    }

    private void pushToDatabase() {
        //firebaseDatabase.getInstance().getReference().child("Singleton").setValue(Singleton.getInstance());
    }
}
