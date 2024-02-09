package com.api.MeteorologicalData.dto.forecast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class List {
    private long dt;
    private Main main;
    private ArrayList<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private long visibility;
    private double pop;
    private Rain rain;
    private Sys sys;
    private String dt_txt;
}
