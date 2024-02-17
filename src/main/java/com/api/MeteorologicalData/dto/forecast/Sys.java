package com.api.MeteorologicalData.dto.forecast;

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
     * A string indicating the weather conditions in the location. Possible values are:
     * "clear sky", "few clouds", "scattered clouds", "broken clouds", "shower rain", "rain", "thunderstorm", "snow", "mist"
     */
    private String pod;
}
