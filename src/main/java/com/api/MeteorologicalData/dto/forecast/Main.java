package com.api.MeteorologicalData.dto.forecast;

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
     * Temperature.
     */
    private double temp;

    /**
     * Temperature.
     */
    private double feelsLike;

    /**
     * Minimum temperature.
     */
    private double tempMin;

    /**
     * Maximum temperature.
     */
    private double tempMax;

    /**
     * Atmospheric pressure on the sea level, hPa.
     */
    private long pressure;

    /**
     * Atmospheric pressure on the sea level, hPa.
     */
    private long seaLevel;

    /**
     * Grnd level pressure, hPa.
     */
    private long grndLevel;

    /**
     * Humidity, %
     */
    private long humidity;

    /**
     * "Feels Like" temperature.
     */
    private double tempKf;
}
