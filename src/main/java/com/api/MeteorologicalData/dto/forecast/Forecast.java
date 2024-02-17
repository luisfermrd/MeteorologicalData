package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * This class is a data transfer object (DTO) that represents the forecast data returned by the OpenWeatherMap API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {

    /**
     * This field contains an error code returned by the OpenWeatherMap API.
     */
    private String cod;

    /**
     * This field contains a message describing the status of the response.
     */
    private long message;

    /**
     * This field contains the number of weather forecasts returned in the response.
     */
    private long cnt;

    /**
     * This field contains an array of weather forecasts.
     */
    private ArrayList<List> list;

    /**
     * This field contains the city information for which the weather forecast is being provided.
     */
    private City city;

}
