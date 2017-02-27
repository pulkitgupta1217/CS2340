package com.cs2340.WaterNet.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pulki on 2/27/2017.
 */

public class Pin {
    LinkedList<Report> reportList = new LinkedList<Report>();
    Location location;
    LinkedList<PurityReport> purityReportList = new LinkedList<PurityReport>();

    public Pin(Report report) {
        reportList.addFirst(report);
        location = new Location(report.getLat(), report.getLng());
    }
    public void addReport(Report report) {
        reportList.addFirst(report);
    }
    public void removeReport(Report report) {
        reportList.remove(report);
    }
    public Report viewReport() {
        try {
            return reportList.getFirst();
        } catch (Exception e) {
            return null;
        }
    }
    public void addPurityReport(PurityReport purityReport) {
        purityReportList.addFirst(purityReport);
    }
    public void removePurityReport(PurityReport purityReport) {
        purityReportList.remove(purityReport);
    }
    public PurityReport viewPurityReport() {
        try {
            return purityReportList.getFirst();
        } catch (Exception e) {
            return null;
        }
    }
    public List<Report> getReports() {
        return reportList;
    }
    public List<PurityReport> getPurityReports() {
        return purityReportList;
    }

}
