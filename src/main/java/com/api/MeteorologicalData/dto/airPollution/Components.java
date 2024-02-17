package com.api.MeteorologicalData.dto.airPollution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the components of the air pollution data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Components {

    /**
     * The amount of carbon monoxide (CO) in parts per million (ppm).
     */
    private double co;

    /**
     * The amount of nitric oxide (NO) in parts per billion (ppb).
     */
    private long no;

    /**
     * The amount of nitrogen dioxide (NO2) in parts per million (ppm).
     */
    private double no2;

    /**
     * The amount of ozone (O3) in parts per billion (ppb).
     */
    private double o3;

    /**
     * The amount of sulfur dioxide (SO2) in parts per million (ppm).
     */
    private double so2;

    /**
     * The amount of fine particulate matter (PM2.5) in micrograms per cubic meter (µg/m³).
     */
    private double pm2_5;

    /**
     * The amount of coarse particulate matter (PM10) in micrograms per cubic meter (µg/m³).
     */
    private double pm10;

    /**
     * The amount of ammonia (NH3) in parts per billion (ppb).
     */
    private double nh3;

}
