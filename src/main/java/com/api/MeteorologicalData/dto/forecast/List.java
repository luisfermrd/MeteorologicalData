package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * This class is a data transfer object that represents the list object in the OpenWeatherMap API response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class List {

    /**
     * The date and time of the forecast, unix, UTC
     */
    private long dt;

    /**
     * The main information about the weather
     */
    private Main main;

    /**
     * An array of weather conditions
     */
    private ArrayList<Weather> weather;

    /**
     * Cloudiness information
     */
    private Clouds clouds;

    /**
     * The wind information
     */
    private Wind wind;

    /**
     * The visibility, in meters
     */
    private long visibility;

    /**
     * The percentage of precipitation
     */
    private double pop;

    /**
     * The rain information
     */
    private Rain rain;

    /**
     * The sys object
     */
    private Sys sys;

    /**
     * The date and time of the forecast, ISO 8601
     */
    private String dt_txt;

}
