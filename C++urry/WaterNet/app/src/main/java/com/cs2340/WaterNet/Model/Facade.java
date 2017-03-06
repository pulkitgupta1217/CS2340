package com.cs2340.WaterNet.Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Pulkit Gupta on 2/28/2017.
 */

public class Facade {
    /**
     * goes between controller and model objects, contains the current user and is populated with the pin list,
     * updated on signin and when necessary
     * basically the ultimate information holder and interfacer.
     * activities only communicate with this and this communicates with the rest of model and
     * returns information to the activity
     */
    private static User currUser = null;
    private static List<Pin> pinList = new LinkedList<Pin>();
    private static List<User> userList = new LinkedList<User>();

    private Facade(){
    }

    private static void start() {
        //set singleton
    }
    private static void reset() {
        //refresh singleton by pushing and pulling
    }

    public static User validateLogin(String email, String pasword) {
        User u = null;
        if (/*user is Valid*/ true) {
            //u = firebase.getUserByUID()
            SecurityLogger.writeNewSecurityLog(Singleton.getInstance().getTime() + " :: " + email + " has logged in");
        }
        Facade.currUser = u;
        return u;
    }
    public static User getCurrUser() {
        return currUser;
    }

    public static void setCurrUser(User currUser) { //may use firebase user instead and generate user based on that
        Facade.currUser = currUser;
    }

    public static List<Pin> getPinList() {
        setPinList();
        return pinList;
    }

    public static void setPinList() {
        //firebase grab pins from db
    }

    public static List<User> getUserList() {
        setUserList();
        return userList;
    }

    public static void setUserList() {
        //firebase grab users from db and filter out admins etc.
    }

    public List<Report> getReports() {
        setPinList();
        List<Report> reports = new LinkedList<Report>();
        for (Pin p : pinList) {
            for (Report r : p.getReports()) {
                reports.add(r);
            }
        }
        return reports;
    }

    public List<Report> getReports(double lat, double lng) {
        setPinList();
        Site site = new Site(lat, lng);
        List<Report> reports = new LinkedList<Report>();
        for (Pin p : pinList) {
            for (Report r : p.getReports()) {
                if (r.getSite().closeTo(site)) {
                    reports.add(r);
                }
            }
        }
        return reports;
    }

    public List<Report> getReports(Site site) {
        return getReports(site.getLat(), site.getLng());
    }

    public List<PurityReport> getPurityReports() {
        setPinList();
        List<PurityReport> purityReports = new LinkedList<PurityReport>();
        for (Pin p : pinList) {
            for (PurityReport pr : p.getPurityReports()) {
                purityReports.add(pr);
            }
        }
        return purityReports;
    }

    public List<PurityReport> getPurityReports(double lat, double lng) {
        setPinList();
        Site site = new Site(lat, lng);
        List<PurityReport> purityReports = new LinkedList<PurityReport>();
        for (Pin p : pinList) {
            for (PurityReport pr : p.getPurityReports()) {
                if (pr.getSite().closeTo(site)) {
                    purityReports.add(pr);
                }
            }
        }
        return purityReports;
    }

    public List<PurityReport> getPurityReports(Site site) {
        return getPurityReports(site.getLat(), site.getLng());
    }

    public static /* google Maps map with pins */ void getMap() {
        getMap(pinList);
    }
    public static /* google Maps map with pins */ void getMap(List<Pin> pins) {
        return /*MapGenerator.getMap(pins)*/;
    }
    public static /*graph*/ void getHistorical(Site site, PurityReport start, PurityReport end) {
        setPinList();
        List<PurityReport> relevant = new LinkedList<PurityReport>();
        for (Pin p : pinList) {
            for (int i = p.getPurityReports().indexOf(start); i < p.getPurityReports().indexOf(end); i++) {
                relevant.add(p.getPurityReports().get(i));
            }
        }
        return /*HistoricalReportGenerator.generate(relevant) */;
    }
}
