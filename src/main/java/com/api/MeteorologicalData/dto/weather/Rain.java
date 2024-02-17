package com.api.MeteorologicalData.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the rain data in the weather API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rain {

    /**
     * The rainfall in millimeters within the last hour.
     */
    @JsonProperty("1h")
    private double the1H;

}
