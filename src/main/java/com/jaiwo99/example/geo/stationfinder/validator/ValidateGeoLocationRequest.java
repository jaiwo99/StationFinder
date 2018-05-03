package com.jaiwo99.example.geo.stationfinder.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GeoLocationRequestValidator.class)
@Documented
public @interface ValidateGeoLocationRequest {

    String message() default "Invalid geolocation request found";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
