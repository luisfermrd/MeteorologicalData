package com.api.MeteorologicalData.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Main weather data for the current day.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Main {
    /**
     * Temperature in Celsius.
     */
    private double temp;
    /**
     * Minimum temperature in Celsius.
     */
    @JsonProperty("temp_min")
    private double tempMin;
    /**
     * Maximum temperature in Celsius.
     */
    @JsonProperty("temp_max")
    private double tempMax;
    /**
     * Feels like temperature in Celsius.
     */
    @JsonProperty("feels_like")
    private double feelsLike;
    /**
     * Atmospheric pressure (in hPa).
     */
    private long pressure;
    /**
     * Humidity (percentage).
     */
    private long humidity;
}
