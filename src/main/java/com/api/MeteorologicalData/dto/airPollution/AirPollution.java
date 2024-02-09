package com.api.MeteorologicalData.dto.airPollution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirPollution {
    private Coord coord;
    private ArrayList<List> list;
}
