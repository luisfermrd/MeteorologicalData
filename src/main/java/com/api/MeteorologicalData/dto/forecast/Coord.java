package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for coordinates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coord {

    /**
     * Latitude coordinate.
     */
    private double lat;

    /**
     * Longitude coordinate.
     */
    private double lon;

}
