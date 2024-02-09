package com.api.MeteorologicalData.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sys {
    private String country;
    private long id;
    private long sunrise;
    private long sunset;
    private long type;
}
