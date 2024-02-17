package com.api.MeteorologicalData.dto.airPollution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Main class for the air pollution API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Main {

    /**
     * Air quality index value.
     */
    private long aqi;

}
