package com.jaiwo99.example.geo.stationfinder.repo;

import com.jaiwo99.example.geo.stationfinder.domain.GeoLocation;
import com.jaiwo99.example.geo.stationfinder.domain.Station;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
public class InMemStationRepo implements StationRepo {

    private static final Map<String, Station> DB = new ConcurrentHashMap<>();

    @Override
    public Stream<Station> search(SearchCmd searchCmd) {
        if (searchCmd.getId() != null) {
            final Station station = DB.get(searchCmd.getId());
            return station == null ? Stream.empty() : Stream.<Station>builder().add(station).build();
        }

        return DB.entrySet()
                .stream()
                .filter(getIdPredicate(searchCmd))
                .filter(getZipcodePredicate(searchCmd))
                .filter(getDistancePredicate(searchCmd))
                .map(Map.Entry::getValue);
    }

    @Override
    public void save(Station station) {
        DB.put(station.getId(), station);
    }

    @Override
    public void deleteAll() {
        DB.clear();
    }

    private Predicate<Map.Entry<String, Station>> getIdPredicate(SearchCmd searchCmd) {
        return kv -> searchCmd.getId() == null || kv.getKey().equals(searchCmd.getId());
    }

    private Predicate<Map.Entry<String, Station>> getZipcodePredicate(SearchCmd searchCmd) {
        return kv -> {
            final String zipcode = kv.getValue().getZipcode();
            return searchCmd.getZipcode() == null || zipcode.equals(searchCmd.getZipcode());
        };
    }

    private Predicate<Map.Entry<String, Station>> getDistancePredicate(SearchCmd searchCmd) {
        return kv -> {

            if (searchCmd.getAirDistanceInMeter() == null) {
                return true;
            }

            final double airDistanceInMeter = searchCmd.getAirDistanceInMeter();
            final GeoLocation location = kv.getValue().getLocation();
            final double distance = location.distanceFrom(searchCmd.getLatitude(), searchCmd.getLongitude());
            return distance <= airDistanceInMeter;
        };
    }
}
