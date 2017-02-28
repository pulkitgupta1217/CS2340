package com.cs2340.WaterNet.Model;

import java.io.Serializable;

/**
 * Created by Pulkit Gupta on 2/26/2017.
 */

public class Site implements Serializable{
    private double lat, lng;
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
}
