package com.cs2340.WaterNet.Model;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Pulkit Gupta on 2/26/2017.
 */

public class Singleton implements Serializable{
    private static Singleton instance = null;


    public long userID;
    public long reportID;
    public long purityReportID;
    private final SimpleDateFormat dateTime;
    private final Calendar c;

    /**
     * the single run constructor that initializes the singleton instance that is stored within
     * the singleton and is required for firebase
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
     * @param updated the singleton stored in firebase
     */
    public static void setInstance(Singleton updated) {
        instance = updated;
    }

    /**
     * get an updated user id from the singleton
     * @return userID++
     */
    public long getUserID() {
        //userID++;
        return userID++;
    }

    /**
     * get an updated report id from the singleton
     * @return report id++
     */
    public long getReportID() {
        //reportID++;
        return reportID++;
    }

    /**
     * get an updated purity report id from the singleton
     * @return purity report id++
     */
    public long getPurityReportID() {
        //purityReportID++;
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

    @Override
    public String toString() {
        return "UID: " + userID + " RID: " + reportID + " PRID: " + purityReportID;
    }
}
