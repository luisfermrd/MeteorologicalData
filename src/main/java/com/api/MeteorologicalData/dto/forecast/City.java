package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a city.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    /**
     * The unique ID of the city.
     */
    private long id;

    /**
     * The name of the city.
     */
    private String name;

    /**
     * The coordinates of the city.
     */
    private Coord coord;

    /**
     * The country of the city.
     */
    private String country;

    /**
     * The population of the city.
     */
    private long population;

    /**
     * The timezone of the city.
     */
    private long timezone;

    /**
     * The time of sunrise of the city.
     */
    private long sunrise;

    /**
     * The time of sunset of the city.
     */
    private long sunset;

}
