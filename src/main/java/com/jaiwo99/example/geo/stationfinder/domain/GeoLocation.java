package com.jaiwo99.example.geo.stationfinder.domain;

import lombok.Data;

@Data
public class GeoLocation {

    public static final double RADIUS_IN_METRES = 6371e3;

    private final double latitude;
    private final double longitude;

    // distance between two geo-locations
    // https://www.movable-type.co.uk/scripts/latlong.html
    public double distanceFrom(double latitude, double longitude) {
        double lat1 = Math.toRadians(this.latitude);
        double long1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(latitude);
        double long2 = Math.toRadians(longitude);
        // apply the spherical law of cosines with a triangle composed of the
        // two locations and the north pole
        double theCos = Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(long1 - long2);
        double arcLength = Math.acos(theCos);
        return arcLength * RADIUS_IN_METRES;
    }
}
