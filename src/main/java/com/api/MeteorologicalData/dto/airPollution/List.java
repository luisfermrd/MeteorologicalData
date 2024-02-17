package com.api.MeteorologicalData.dto.airPollution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the List object in the API response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class List {

    /**
     * The main object containing the pollution data.
     */
    private Main main;

    /**
     * The components object containing the pollution components.
     */
    private Components components;

    /**
     * The time difference in milliseconds between the current data and the current time.
     */
    private long dt;

}
