package com.cs2340.WaterNet.Model;

import java.io.Serializable;

/**
 * Created by Pulkit Gupta on 2/26/2017.
 */

public class Report implements Serializable {
    private String creator, dateTime;
    private Long reportID;
    private Site site;
    private WaterType waterType;
    private WaterCondition waterCondition;

    public Report(String creator, double lat, double lng, WaterType waterType, WaterCondition waterCondition) {
        this.creator = creator;
        site = new Site(lat, lng);
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        dateTime = Singleton.getInstance().getTime();
        reportID = Singleton.getInstance().getReportID();
    }
    public long getReportID() {
        return reportID;
    }
    public double getLng() {
        return site.getLng();
    }
    public double getLat() {
        return site.getLat();
    }
    public String getCreator() {
        return creator;
    }
    public String getDateTime() {
        return dateTime;
    }
    public WaterCondition getWaterCondition() {
        return waterCondition;
    }
    public WaterType getWaterType() {
        return waterType;
    }

}
