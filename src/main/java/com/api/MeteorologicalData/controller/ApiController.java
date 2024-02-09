package com.api.MeteorologicalData.controller;

import com.api.MeteorologicalData.dto.location.LocationData;
import com.api.MeteorologicalData.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ApiService apiService;

    @GetMapping("/geo/{city}")
    public ResponseEntity<LocationData[]> location(@PathVariable("city") String city){
        return new ResponseEntity<LocationData[]>(apiService.findCity(city),HttpStatus.OK);
    }

    @GetMapping("/weather/{city}")
    public ResponseEntity<?> currentWeather(@PathVariable("city") String city){
        return apiService.currentWeather(city);
    }

    @GetMapping("/forecast/{city}")
    public ResponseEntity<?> forecast(@PathVariable("city") String city){
        return apiService.forecast(city);
    }

    @GetMapping("/air-pollution/{city}")
    public ResponseEntity<?> airPollution(@PathVariable("city") String city){
        return apiService.airPollution(city);
    }
}
