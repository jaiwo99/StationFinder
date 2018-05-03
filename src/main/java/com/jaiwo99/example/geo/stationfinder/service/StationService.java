package com.jaiwo99.example.geo.stationfinder.service;

import com.jaiwo99.example.geo.stationfinder.repo.SearchCmd;
import com.jaiwo99.example.geo.stationfinder.repo.StationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepo stationRepo;

    public List<StationSearchDTO> search(SearchCmd searchCmd) {
        return stationRepo.search(searchCmd).map(e -> {
            final double distance = searchCmd.getAirDistanceInMeter() != null ?
                    e.getLocation().distanceFrom(searchCmd.getLatitude(), searchCmd.getLongitude()) :
                    -1;
            return StationSearchDTO.builder()
                    .id(e.getId())
                    .zipcode(e.getZipcode())
                    .latitude(e.getLocation().getLatitude())
                    .longitude(e.getLocation().getLongitude())
                    .distance(distance)
                    .build();
        }).collect(Collectors.toList());
    }

    public void save(StationSaveCmd stationSaveCmd) {
        stationRepo.save(stationSaveCmd.toStation());
    }
}
