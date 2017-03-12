package com.cs2340.WaterNet.Model;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;

/**
 * a pin object that stores all of the reports and purity reports close enough to its point
 * it contains a site that is the gps coordinate of the first report that was added to it
 * Created by Pulkit Gupta on 2/27/2017.
 */

public class Pin {
    private LinkedList<Report> reportList = new LinkedList<Report>();
    private Site site;
    private LinkedList<PurityReport> purityReportList = new LinkedList<PurityReport>();
    private Marker marker;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    /**
     * creates a pin based on the report that was passed in
     * @param report the first report at a new location
     */
    public Pin(Report report) {
        reportList.addFirst(report);
        site = new Site(report.getLat(), report.getLng());
    }

    /**
     * adds a report to the pin
     * @param report a report to be added if it is closeEnough
     */
    public void addReport(Report report) {
        reportList.addFirst(report);
    }

    /**
     * removes a report from the pin
     * @param report the report to be removed
     */
    public void removeReport(Report report) {
        reportList.remove(report);
    }

    /**
     * gets the first report from the list of reports
     * @return the first report in the list and if the list is empty or does not exist, returns null
     */
    public Report viewReport() {
        try {
            return reportList.getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * adds a purity report to the front of the purity report list
     * @param purityReport the purity report to be added
     */
    public void addPurityReport(PurityReport purityReport) {
        purityReportList.addFirst(purityReport);
    }

    /**
     * removes the passed in purity report from the list
     * @param purityReport the report to be removed
     */
    public void removePurityReport(PurityReport purityReport) {
        purityReportList.remove(purityReport);
    }

    /**
     * gets the most recently added PurityReport
     * @return the first purity report in the corresponding list
     */
    public PurityReport viewPurityReport() {
        try {
            return purityReportList.getFirst();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get the longitude of the pin's site
     * @return longitude as a double
     */
    public double getLng() {
        return site.getLng();
    }

    /**
     * get the latitude of the pin's site
     * @return latitude as a double
     */
    public double getLat() {
        return site.getLat();
    }

    /**
     * gets the list of reports
     * @return reportList
     */
    public List<Report> getReports() {
        return reportList;
    }

    /**
     * gets the list of PurityReports
     * @return purityReportList
     */
    public List<PurityReport> getPurityReports() {
        return purityReportList;
    }

    public String reportListString() {
        String s = "";
        for (Report r: reportList) {
            s += r.toString() + "\n";
        }

        return s;
    }



}
