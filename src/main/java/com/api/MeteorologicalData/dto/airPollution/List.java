package com.api.MeteorologicalData.dto.airPollution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class List {
    private Main main;
    private Components components;
    private long dt;
}
