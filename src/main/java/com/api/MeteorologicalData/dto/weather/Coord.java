package com.api.MeteorologicalData.dto.weather;

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
     * The latitude of the location.
     */
    private double lat;

    /**
     * The longitude of the location.
     */
    private double lon;

}
