package com.api.MeteorologicalData.dto.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationData {
    private String country;
    private double lat;
    @JsonProperty("local_names")
    @JsonIgnore
    private Map<String, String> localNames;
    private double lon;
    private String name;
    private String state;

}
