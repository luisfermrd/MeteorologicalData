package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wind data model.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wind {

    /**
     * Wind speed in m/s.
     */
    private double speed;

    /**
     * Wind direction in degrees (meteorological).
     */
    private long deg;

    /**
     * Wind gust in m/s.
     */
    private double gust;

}
