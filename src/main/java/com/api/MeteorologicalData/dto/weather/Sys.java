package com.api.MeteorologicalData.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is a data transfer object (DTO) that represents the "sys" object in the OpenWeatherMap API response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sys {

    /**
     * The country code (GB, JP, etc.)
     */
    private String country;

    /**
     * The internal OpenWeatherMap ID
     */
    private long id;

    /**
     * The sunrise time, unix, UTC
     */
    private long sunrise;

    /**
     * The sunset time, unix, UTC
     */
    private long sunset;

    /**
     * The type of weather system (clear, rain, snow, etc.)
     */
    private long type;

}
