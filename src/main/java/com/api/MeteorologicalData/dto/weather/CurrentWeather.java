package com.api.MeteorologicalData.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeather {
    private String base;
    private Clouds clouds;
    private long cod;
    private Coord coord;
    private long dt;
    private long id;
    private Main main;
    private String name;
    private Rain rain;
    private Sys sys;
    private long timezone;
    private long visibility;
    private Weather[] weather;
    private Wind wind;
}
