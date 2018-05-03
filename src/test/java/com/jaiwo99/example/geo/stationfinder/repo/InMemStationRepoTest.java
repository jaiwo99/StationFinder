package com.jaiwo99.example.geo.stationfinder.repo;

import com.jaiwo99.example.geo.stationfinder.domain.Station;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemStationRepoTest {

    private InMemStationRepo repo;

    @Before
    public void setup() {
        repo = new InMemStationRepo();
    }

    @Test
    public void deleteAll_should_delete_all_entries() {
        repo.save(new Station("id", "zipcode", 1d, 1d));
        assertThat(repo.search(new SearchCmd()).count()).isEqualTo(1L);

        repo.deleteAll();
        assertThat(repo.search(new SearchCmd()).count()).isEqualTo(0L);
    }

    @Test
    public void save_should_save_entry() {
        assertThat(repo.search(new SearchCmd()).count()).isEqualTo(0L);

        repo.save(new Station("id", "zipcode", 1d, 1d));
        assertThat(repo.search(new SearchCmd()).count()).isEqualTo(1L);
    }

    @Test
    public void search_should_work_with_id() {
        final Station station = new Station("id", "zipcode", 1d, 1d);
        repo.save(station);

        final SearchCmd cmd = SearchCmd.builder().id("id").build();

        final List<Station> stationList = repo.search(cmd).collect(Collectors.toList());
        assertThat(stationList).hasSize(1);
        final Station result = stationList.get(0);

        assertThat(result).isEqualTo(station);
    }

    @Test
    public void search_should_work_with_zipcode() {
        final Station station = new Station("id", "zipcode", 1d, 1d);
        repo.save(station);

        final SearchCmd cmd = SearchCmd.builder().zipcode("zipcode").build();

        final List<Station> stationList = repo.search(cmd).collect(Collectors.toList());
        assertThat(stationList).hasSize(1);
        final Station result = stationList.get(0);

        assertThat(result).isEqualTo(station);
    }

    @Test
    public void search_should_work_with_geo_search() {
        final Station station = new Station("id", "zipcode", 1d, 1d);
        repo.save(station);

        final SearchCmd cmd = SearchCmd.builder().airDistanceInMeter(100d).latitude(1d).longitude(1d).build();

        final List<Station> stationList = repo.search(cmd).collect(Collectors.toList());
        assertThat(stationList).hasSize(1);
        final Station result = stationList.get(0);

        assertThat(result).isEqualTo(station);
    }

    @Test
    public void search_should_be_able_to_find_all() {
        repo.save(new Station("id", "zipcode", 1d, 1d));
        repo.save(new Station("id2", "zipcode", 1d, 1d));
        repo.save(new Station("id3", "zipcode", 1d, 1d));

        assertThat(repo.search(new SearchCmd()).count()).isEqualTo(3);
    }

}