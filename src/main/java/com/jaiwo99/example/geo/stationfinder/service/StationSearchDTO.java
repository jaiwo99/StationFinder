package com.jaiwo99.example.geo.stationfinder.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StationSearchDTO {

    private final String id;
    private final String zipcode;
    private final double latitude;
    private final double longitude;
    private final double distance;
}
