package com.api.MeteorologicalData.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the current weather data returned by the OpenWeatherMap API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeather {

    /**
     * The base type of the weather, e.g. "stations" for the weather at the nearest weather station.
     */
    private String base;

    /**
     * The clouds data for the current weather conditions.
     */
    private Clouds clouds;

    /**
     * The OpenWeatherMap API code.
     */
    private long cod;

    /**
     * The coordinate data for the current weather conditions.
     */
    private Coord coord;

    /**
     * The UNIX timestamp of the current weather data.
     */
    private long dt;

    /**
     * The weather condition id.
     */
    private long id;

    /**
     * The main weather data for the current conditions.
     */
    private Main main;

    /**
     * The name of the city.
     */
    private String name;

    /**
     * The rain data for the current weather conditions.
     */
    private Rain rain;

    /**
     * The system data for the current weather conditions.
     */
    private Sys sys;

    /**
     * The timezone offset in seconds.
     */
    private long timezone;

    /**
     * The visibility in meters.
     */
    private long visibility;

    /**
     * The weather data for the current conditions.
     */
    private Weather[] weather;

    /**
     * The wind data for the current weather conditions.
     */
    private Wind wind;

}
