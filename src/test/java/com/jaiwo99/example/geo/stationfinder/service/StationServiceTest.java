package com.jaiwo99.example.geo.stationfinder.service;

import com.jaiwo99.example.geo.stationfinder.domain.Station;
import com.jaiwo99.example.geo.stationfinder.repo.SearchCmd;
import com.jaiwo99.example.geo.stationfinder.repo.StationRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StationServiceTest {

    @Mock
    private StationRepo stationRepo;
    @InjectMocks
    private StationService stationService;


    @Test
    public void save_should_invoke_stationRepo() {
        final StationSaveCmd stationSaveCmd = new StationSaveCmd("id", "zipcode", 1d, 1d);
        stationService.save(stationSaveCmd);

        verify(stationRepo).save(eq(stationSaveCmd.toStation()));
    }

    @Test
    public void search_should_return_negative_value_for_distance_if_is_not_a_geo_search() {
        final SearchCmd cmd = SearchCmd.builder().id("id").build();
        final Station station = new Station("id", "zipcode", 1d, 1d);

        when(stationRepo.search(any())).thenReturn(Stream.of(station));

        final StationSearchDTO result = stationService.search(cmd).get(0);
        assertThat(result.getDistance()).isEqualTo(-1);
    }

    @Test
    public void search_should_return_distance_for_geo_search() {
        final SearchCmd cmd = SearchCmd.builder().latitude(1.00001d).longitude(1.00001d).airDistanceInMeter(100d).build();
        final Station station = new Station("id", "zipcode", 1d, 1d);

        when(stationRepo.search(any())).thenReturn(Stream.of(station));

        final StationSearchDTO result = stationService.search(cmd).get(0);
        assertThat(result.getDistance()).isGreaterThan(0);
    }

    @Test
    public void search_should_transform_station_to_stationDTO() {
        final SearchCmd cmd = SearchCmd.builder().latitude(1.00001d).longitude(1.00001d).airDistanceInMeter(100d).build();
        final Station station = new Station("id", "zipcode", 1d, 1d);

        when(stationRepo.search(any())).thenReturn(Stream.of(station));
        final StationSearchDTO result = stationService.search(cmd).get(0);
        assertThat(result.getId()).isEqualTo(station.getId());
        assertThat(result.getZipcode()).isEqualTo(station.getZipcode());
        assertThat(result.getLatitude()).isEqualTo(station.getLocation().getLatitude());
        assertThat(result.getLongitude()).isEqualTo(station.getLocation().getLongitude());
        assertThat(result.getDistance()).isGreaterThan(0);
    }
}