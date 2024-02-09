package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    private long id;
    private String name;
    private Coord coord;
    private String country;
    private long population;
    private long timezone;
    private long sunrise;
    private long sunset;
}
