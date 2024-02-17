package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for clouds.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clouds {

    /**
     * Cloudiness, expressed in percent.
     */
    private long all;

}
