package com.jaiwo99.example.geo.stationfinder.validator;

import com.jaiwo99.example.geo.stationfinder.repo.SearchCmd;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GeoLocationRequestValidator implements ConstraintValidator<ValidateGeoLocationRequest, SearchCmd> {
   public void initialize(ValidateGeoLocationRequest constraint) {
   }

   public boolean isValid(SearchCmd cmd, ConstraintValidatorContext context) {

      // valid if all parameters not exist
      if (cmd.getAirDistanceInMeter() == null && cmd.getLatitude() == null && cmd.getLongitude() == null) {
         return true;
      }

      // valid if all parameters exist
      if (cmd.getAirDistanceInMeter() != null && cmd.getLatitude() != null && cmd.getLongitude() != null) {
         return true;
      }

      return false;
   }
}
