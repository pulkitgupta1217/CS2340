package com.cs2340.WaterNet.Model;

import java.io.Serializable;

/**
 * Created by Pulkit Gupta on 2/26/2017.
 */

public class Site implements Serializable, Comparable<Site>{
    private double lat, lng;
    private static final double closeEnough = 1.0/3600;
    /**
     * default constructor required by firebase
     */
    public Site() {
    }

    /**
     * constructor that takes a lat, long pair
     * @param lat the latitude
     * @param lng the longitude
     */
    public Site(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * set the latitude of this site if it needs to be changed
     * @param lat the new latitude
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * set the longitude of the site if itt needs to be changed
     * @param lng the new longitude
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * get the latitude of the site
     * @return the latitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * get the longitude of the site
     * @return the longitude
     */
    public double getLng() {
        return lng;
    }

    /**
     * determines if two sites are close enough
     * @param site the site to compare to
     * @return true if the site is within closeEnough variable
     */
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

    public boolean equals(Site s) {
        return closeTo(s);
    }

    public int compareTo(Site s) {
        if (equals(s)) {
            return 0;
        }
        return -1;
    }

}
