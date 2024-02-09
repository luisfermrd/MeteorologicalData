package com.api.MeteorologicalData.dto.airPollution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Components {
    private double co;
    private long no;
    private double no2;
    private double o3;
    private double so2;
    private double pm2_5;
    private double pm10;
    private double nh3;
}
