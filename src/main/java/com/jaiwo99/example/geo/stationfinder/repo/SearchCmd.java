package com.jaiwo99.example.geo.stationfinder.repo;

import com.jaiwo99.example.geo.stationfinder.validator.ValidateGeoLocationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidateGeoLocationRequest
public class SearchCmd {

    private String id;
    private String zipcode;
    private Double latitude;
    private Double longitude;
    private Double airDistanceInMeter;
}
