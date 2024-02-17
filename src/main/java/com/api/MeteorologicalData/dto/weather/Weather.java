package com.api.MeteorologicalData.dto.weather;

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
     * A textual description of the weather condition.
     */
    private String description;

    /**
     * A code indicating the icon used to represent the weather condition.
     */
    private String icon;

    /**
     * A unique ID for the weather condition.
     */
    private long id;

    /**
     * The main weather parameter, indicating the main feature of the weather, e.g., "Rain" or "Snow".
     */
    private String main;

}
