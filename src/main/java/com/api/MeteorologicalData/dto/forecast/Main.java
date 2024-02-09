package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Main {
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private long pressure;
    private long sea_level;
    private long grnd_level;
    private long humidity;
    private double temp_kf;
}
