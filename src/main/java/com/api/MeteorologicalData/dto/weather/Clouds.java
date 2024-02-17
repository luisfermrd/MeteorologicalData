package com.api.MeteorologicalData.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for clouds data.
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
