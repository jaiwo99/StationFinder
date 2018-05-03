package com.jaiwo99.example.geo.stationfinder.web;

import com.jaiwo99.example.geo.stationfinder.repo.SearchCmd;
import com.jaiwo99.example.geo.stationfinder.service.StationSaveCmd;
import com.jaiwo99.example.geo.stationfinder.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class StationRestController {

    private final StationService stationService;

    @GetMapping("/stations/search")
    public StationSearchResponse search(@Valid SearchCmd searchCmd) {
        return StationSearchResponse.builder().content(stationService.search(searchCmd)).build();
    }

    @PostMapping("/stations")
    public void save(@RequestBody @Valid StationSaveCmd saveCmd) {
        stationService.save(saveCmd);
    }
}
