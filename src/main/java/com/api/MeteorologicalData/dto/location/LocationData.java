package com.api.MeteorologicalData.dto.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Data Transfer Object for location data.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationData {

    /**
     * The country of the location.
     */
    private String country;

    /**
     * The latitude of the location.
     */
    private double lat;

    /**
     * A map of local names for the location, keyed by language.
     */
    @JsonProperty("local_names")
    @JsonIgnore
    private Map<String, String> localNames;

    /**
     * The longitude of the location.
     */
    private double lon;

    /**
     * The name of the location.
     */
    private String name;

    /**
     * The state of the location.
     */
    private String state;

}
