package com.jaiwo99.example.geo.stationfinder.web;

import com.jaiwo99.example.geo.stationfinder.service.StationSearchDTO;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class StationSearchResponse {

    private List<StationSearchDTO> content;
}
