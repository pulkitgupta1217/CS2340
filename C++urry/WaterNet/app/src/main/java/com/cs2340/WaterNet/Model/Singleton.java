package com.cs2340.WaterNet.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Pulkit Gupta on 2/26/2017.
 */

public class Singleton implements Serializable{
    private static Singleton instance = null;


    private int userID;
    private int reportID;
    private int purityReportID;
    private SimpleDateFormat dateTime;
    private Calendar c;

    private Singleton(){
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
    public void setInstance(Singleton fromdb) {
        this.instance = fromdb;
    }

    public int getUserID() {
        userID++;
        return userID;
    }
    public int getReportID() {
        reportID++;
        return reportID;
    }
    public int getPurityReportID() {
        purityReportID++;
        return purityReportID;
    }
    public String getTime() {
        return dateTime.format(c.getTime());
    }
}
