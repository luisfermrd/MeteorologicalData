package com.api.MeteorologicalData.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    private String description;
    private String icon;
    private long id;
    private String main;
}
