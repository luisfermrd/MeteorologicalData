package com.api.MeteorologicalData.dto.airPollution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * This class represents the AirPollution data type in the Weather API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirPollution {

    /**
     * The coordinates of the location where the air pollution data was measured.
     */
    private Coord coord;

    /**
     * A list of air pollution data points.
     */
    private ArrayList<List> list;

}
