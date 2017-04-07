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

    /**
     * constructor required for firebase
     */
    public Report() {

    }

    /**
     * creates a report with the following instance data
     * @param creator the username of the user who made the report
     * @param lat the latitude of the water source being reported on
     * @param lng the longitude of the water source being reported on
     * @param waterType the enum representing the type of water
     * @param waterCondition the enum representing the water condition
     */
    public Report(String creator, double lat, double lng, WaterType waterType, WaterCondition waterCondition) {
        this.creator = creator;
        site = new Site(lat, lng);
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        dateTime = Singleton.getInstance().getTime();
        reportID = Singleton.getInstance().getReportID();
    }

    /**
     * get the report id of the Report
     * @return the report id
     */
    public long getReportID() {
        return reportID;
    }

    /**
     * get the longitude of the report location
     * @return the longitude of the site object
     */
    public double getLng() {
        return site.getLng();
    }

    /**
     * get the latitude of the report location
     * @return the latitude of the site object
     */
    public double getLat() {
        return site.getLat();
    }

    /**
     * get the site object of the report in order to perform site related operations
     * @return the site
     */
    public Site getSite() {
        return site;
    }

    /**
     * get the username of the user who made the report
     * @return the creator string
     */
    public String getCreator() {
        return creator;
    }

    /**
     * get the string form of the date/time when the report was created
     * @return the datetime string
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * get the condition of the water at this site
     * @return the water condition enum
     */
    public WaterCondition getWaterCondition() {
        return waterCondition;
    }

    /**
     * get the type of water at this site
     * @return the water type enum
     */
    public WaterType getWaterType() {
        return waterType;
    }

    public String toString() {

        return creator + " " + dateTime +" " +  waterType.toString() + " " +  waterCondition.toString();

    }

}
