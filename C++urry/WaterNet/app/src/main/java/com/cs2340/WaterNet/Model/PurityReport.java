package com.cs2340.WaterNet.Model;

/**
 * Created by Pulkit Gupta on 2/27/2017.
 */

public class PurityReport {
    private String creator, dateTime;
    private Long purityReportID;
    private Site site;
    private Virus virus;
    private Contaminant contaminant;
    private OverallCondition overallCondition;

    /**
     * constructor required for firebase
     */
    public PurityReport() {

    }

    /**
     * creates a PurityReport
     * @param creator string containing username of creator
     * @param lat latitude
     * @param lng longitude
     * @param virus enum for viruses
     * @param contaminant enum for contaminants
     * @param overallCondition enum for overall condition
     */
    public PurityReport(String creator, double lat, double lng, Virus virus, Contaminant contaminant, OverallCondition overallCondition) {
        this.creator = creator;
        dateTime = Singleton.getInstance().getTime();
        purityReportID = Singleton.getInstance().getPurityReportID();
        site = new Site(lat, lng);
        this.virus = virus;
        this.contaminant = contaminant;
        this.overallCondition = overallCondition;
    }

    /**
     * get the username of the user who was logged in and created this PurityReport
     * @return string format of the username
     */
    public String getCreator() {
        return creator;
    }

    /**
     * get the time of when this report was created
     * @return the datetime as a string
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * get the ID (assigned as it is made)
     * @return the id of the purityReport
     */
    public long getPurityReportID() {
        return purityReportID;
    }


    /*
    public void setPurityReportID(long purityReportID) {
        this.purityReportID = purityReportID;
    }
    */

    /**
     * get the Virus enum of the purityReport
     * @return virus
     */
    public Virus getVirus() {
        return virus;
    }

    /**
     * reassign the virus of the report
     * @param virus the new Virus enum
     */
    public void setVirus(Virus virus) {
        this.virus = virus;
    }

    /**
     * get the Contaminant enum of the purityReport
     * @return contaminant
     */
    public Contaminant getContaminant() {
        return contaminant;
    }

    /**
     * reassign the Contaminant enum of the purityReport
     * @param contaminant the new Contaminant enum
     */
    public void setContaminant(Contaminant contaminant) {
        this.contaminant = contaminant;
    }

    /**
     * get the OverallCondition enum of the purityReport
     * @return overallCondition
     */
    public OverallCondition getOverallCondition() {
        return overallCondition;
    }

    /**
     * reassign the OverallCondition enum
     * @param overallCondition the new OverallCondition
     */
    public void setOverallCondition(OverallCondition overallCondition) {
        this.overallCondition = overallCondition;
    }

    /**
     * set the new longitude
     * @param lng new longitude
     */
    public void setLng(double lng) {
        site.setLng(lng);
    }

    /**
     * set the new latitude
     * @param lat the new latitude
     */
    public void setLat(double lat) {
        site.setLat(lat);
    }

    /**
     * get the longitude
     * @return longitude
     */
    public double getLng() {
        return site.getLng();
    }

    /**
     * get the latitude
     * @return latitude
     */
    public double getLat() {
        return site.getLat();
    }

    /**
     * get the site object of the purity report
     * @return site
     */
    public Site getSite() {
        return site;
    }
}
