package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {
    private String cod;
    private long message;
    private long cnt;
    private ArrayList<List> list;
    private City city;
}
