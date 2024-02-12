package com.api.MeteorologicalData.service;

import com.api.MeteorologicalData.dto.location.LocationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApiServiceTest {

    @Autowired
    private ApiService apiService;
    private String city;

    private LocationData[] locationData;

    @BeforeEach
    void setUp() {
        this.city = "Bogota";
        this.locationData = apiService.findCity(this.city);
    }

    @Test
    void findCityFound() {
        assertNotNull(this.locationData);
    }

    @Test
    void findCityNotFound() {
        LocationData[] locationData2 = apiService.findCity("doesNotExist");
        assertEquals(0, locationData2.length);
    }

    @Test
    void currentWeather() {
        assertNotNull(apiService.currentWeather(this.city, this.locationData[0]));
    }

    @Test
    void forecast() {
        assertNotNull(apiService.forecast(this.city, this.locationData[0]));
    }

    @Test
    void airPollution() {
        assertNotNull(apiService.airPollution(this.city, this.locationData[0]));
    }
}