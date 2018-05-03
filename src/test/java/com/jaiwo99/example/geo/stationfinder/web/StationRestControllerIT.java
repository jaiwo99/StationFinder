package com.jaiwo99.example.geo.stationfinder.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaiwo99.example.geo.stationfinder.domain.Station;
import com.jaiwo99.example.geo.stationfinder.repo.SearchCmd;
import com.jaiwo99.example.geo.stationfinder.repo.StationRepo;
import com.jaiwo99.example.geo.stationfinder.service.StationSaveCmd;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StationRestControllerIT {

    @Autowired
    private StationRepo stationRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Station station1;
    private Station station2;
    private Station station3;

    @Before
    public void setUp() throws Exception {
        stationRepo.deleteAll();

        station1 = new Station("id1", "10115", 1.000000d, 1.000000d);
        station2 = new Station("id2", "10969", 1.000005d, 1.000005d);
        station3 = new Station("id3", "10888", 2.000000d, 2.000000d);

        stationRepo.save(station1);
        stationRepo.save(station2);
        stationRepo.save(station3);
    }

    @Test
    public void search_should_query_by_id() throws Exception {
        mockMvc.perform(get("/stations/search").param("id", "id1"))
                .andDo(print())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", equalTo("id1")));
    }

    @Test
    public void search_should_query_by_zipcode() throws Exception {
        mockMvc.perform(get("/stations/search").param("zipcode", "10888"))
                .andDo(print())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", equalTo("id3")));
    }

    @Test
    public void search_should_query_by_geolocation() throws Exception {
        mockMvc.perform(get("/stations/search")
                .param("latitude", "1")
                .param("longitude", "1")
                .param("airDistanceInMeter", "100")
        )
                .andDo(print())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", equalTo("id2")))
                .andExpect(jsonPath("$.content[1].id", equalTo("id1")));
    }

    @Test
    public void search_should_validate_geolocation_query() throws Exception {
        mockMvc.perform(get("/stations/search")
                .param("latitude", "1")
                .param("longitude", "1")
        )
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void save_should_save_new_station() throws Exception {
        final StationSaveCmd cmd = StationSaveCmd.builder()
                .id("id4")
                .zipcode("12345")
                .latitude(1d)
                .longitude(1d)
                .build();

        mockMvc.perform(post("/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cmd)))
                .andDo(print())
                .andExpect(status().is(200));

        final List<Station> stations = stationRepo.search(SearchCmd.builder().id("id4").build()).collect(Collectors.toList());
        assertThat(stations).hasSize(1);
        assertThat(stations.get(0)).isEqualTo(cmd.toStation());
    }

    @Test
    public void save_should_save_existing_station() throws Exception {
        final StationSaveCmd cmd = StationSaveCmd.builder()
                .id(station1.getId())
                .zipcode("54321")
                .latitude(station1.getLocation().getLatitude())
                .longitude(station1.getLocation().getLongitude())
                .build();

        mockMvc.perform(post("/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cmd)))
                .andDo(print())
                .andExpect(status().is(200));

        final List<Station> stations = stationRepo.search(SearchCmd.builder().id(station1.getId()).build()).collect(Collectors.toList());
        assertThat(stations).hasSize(1);
        assertThat(stations.get(0).getZipcode()).isEqualTo("54321");
    }

    @Test
    public void save_should_requires_id() throws Exception {
        final StationSaveCmd cmd = StationSaveCmd.builder()
                .id(null)
                .zipcode("12345")
                .latitude(1d)
                .longitude(1d)
                .build();

        mockMvc.perform(post("/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cmd)))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void save_should_requires_zipcode() throws Exception {
        final StationSaveCmd cmd = StationSaveCmd.builder()
                .id("id4")
                .zipcode(null)
                .latitude(1d)
                .longitude(1d)
                .build();

        mockMvc.perform(post("/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cmd)))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void save_should_requires_latitude() throws Exception {
        final StationSaveCmd cmd = StationSaveCmd.builder()
                .id("id4")
                .zipcode("zipcode")
                .latitude(null)
                .longitude(1d)
                .build();

        mockMvc.perform(post("/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cmd)))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void save_should_requires_longitude() throws Exception {
        final StationSaveCmd cmd = StationSaveCmd.builder()
                .id("id4")
                .zipcode("zipcode")
                .latitude(1d)
                .longitude(null)
                .build();

        mockMvc.perform(post("/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cmd)))
                .andDo(print())
                .andExpect(status().is(400));
    }
}