package com.jaiwo99.example.geo.stationfinder.validator;

import com.jaiwo99.example.geo.stationfinder.repo.SearchCmd;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class GeoLocationRequestValidatorTest {

    private Validator validator;

    @Before
    public void init() {

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();

    }

    @Test
    public void geoLocationRequest_is_valid_when_all_value_is_null() {
        final SearchCmd searchCmd = SearchCmd.builder().build();
        final Set<ConstraintViolation<SearchCmd>> violations = validator.validate(searchCmd);

        assertThat(violations).hasSize(0);
    }

    @Test
    public void geoLocationRequest_is_invalid_if_not_all_geo_value_set() {
        final SearchCmd searchCmd = SearchCmd.builder().latitude(1d).build();
        final Set<ConstraintViolation<SearchCmd>> violations = validator.validate(searchCmd);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void geoLocationRequest_is_valid_if_all_geo_value_set() {
        final SearchCmd searchCmd = SearchCmd.builder().latitude(1d).longitude(1d).airDistanceInMeter(1d).build();
        final Set<ConstraintViolation<SearchCmd>> violations = validator.validate(searchCmd);

        assertThat(violations).hasSize(0);
    }


}