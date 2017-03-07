package com.cs2340.WaterNet.Model;

import java.io.Serializable;

/**
 * Created by Pulkit Gupta on 2/26/2017.
 */

public class Site implements Serializable{
    private double lat, lng;
    private static final double closeEnough = 0.1;

    public Site() {
    }

    public Site(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
    public double getLat() {
        return lat;
    }
    public double getLng() {
        return lng;
    }

    public boolean closeTo(Site site) {
        if (Math.abs(lat - site.getLat()) < closeEnough) {
            if (Math.abs(lng - site.getLng()) < closeEnough) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return lat + " " + lng;
    }
}
