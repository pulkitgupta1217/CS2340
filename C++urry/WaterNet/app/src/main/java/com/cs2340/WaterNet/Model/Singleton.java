package com.cs2340.WaterNet.Model;

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

    public Singleton(){
        userID = 0;
        reportID = 0;
        purityReportID = 0;
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
        userID++;
        return userID;
    }
    public long getUserIDNoIncrement() {
        return userID;
    }
    public long getReportID() {
        reportID++;
        return reportID;
    }
    public long getPurityReportID() {
        purityReportID++;
        return purityReportID;
    }
    public String getTime() {
        return dateTime.format(c.getTime());
    }
}
