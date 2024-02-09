package com.api.MeteorologicalData.dto.airPollution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coord {
    private double lon;
    private double lat;
}
