package com.api.MeteorologicalData.dto.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Rain
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rain {

    /**
     * Rainfall in mm within the next 3 hours
     */
    @JsonProperty("3h")
    private double _3h;

}
