package com.jaiwo99.example.geo.stationfinder.repo;

import com.jaiwo99.example.geo.stationfinder.domain.Station;

import java.util.stream.Stream;

public interface StationRepo {

    Stream<Station> search(SearchCmd searchCmd);

    void save(Station station);

    void deleteAll();
}
