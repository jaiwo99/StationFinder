package com.jaiwo99.example.geo.stationfinder.service;

import com.jaiwo99.example.geo.stationfinder.domain.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationSaveCmd {

    @NotBlank
    private String id;
    @NotBlank
    private String zipcode;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

    public Station toStation() {
        return new Station(id, zipcode, latitude, longitude);
    }
}
