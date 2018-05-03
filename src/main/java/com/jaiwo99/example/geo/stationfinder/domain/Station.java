package com.jaiwo99.example.geo.stationfinder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    private String id;
    private String zipcode;
    private GeoLocation location;

    public Station(String id, String zipcode, double latitude, double longitude) {
        this.id = id;
        this.zipcode = zipcode;
        this.location = new GeoLocation(latitude, longitude);
    }
}
