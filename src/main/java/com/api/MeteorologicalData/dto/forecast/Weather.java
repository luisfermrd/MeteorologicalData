package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the weather data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    /**
     * The unique identifier of the weather data.
     */
    private long id;

    /**
     * The main weather condition, e.g. "Clouds".
     */
    private String main;

    /**
     * A text description of the weather condition.
     */
    private String description;

    /**
     * The icon representing the weather condition.
     */
    private String icon;

}
