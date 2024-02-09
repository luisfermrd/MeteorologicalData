package com.api.MeteorologicalData.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Main {
    @JsonProperty("feels_like")
    private double feelsLike;
    private long humidity;
    private long pressure;
    private double temp;
    @JsonProperty("temp_max")
    private double tempMax;
    @JsonProperty("temp_min")
    private double tempMin;
}
