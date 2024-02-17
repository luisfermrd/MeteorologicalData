package com.api.MeteorologicalData.dto.airPollution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the coordinates of a location.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coord {

    /**
     * The longitude of the location.
     */
    private double lon;

    /**
     * The latitude of the location.
     */
    private double lat;

}
