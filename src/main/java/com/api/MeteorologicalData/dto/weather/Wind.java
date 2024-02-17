package com.api.MeteorologicalData.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wind class holds information about the wind speed and direction.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wind {

    /**
     * Wind direction in degrees (meteorological)
     */
    private long deg;

    /**
     * Wind speed.
     */
    private double speed;

}
