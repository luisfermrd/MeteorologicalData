package com.api.MeteorologicalData.service;

import com.api.MeteorologicalData.dto.location.LocationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for the ApiService class.
 */
@SpringBootTest
public class ApiServiceTest {

    /**
     * The ApiService under test.
     */
    @Autowired
    private ApiService apiService;

    /**
     * The city used for testing.
     */
    private String city;

    /**
     * The LocationData returned by the findCity method.
     */
    private LocationData[] locationData;

    /**
     * Sets up the tests by finding the location data for the test city.
     */
    @BeforeEach
    void setUp() {
        this.city = "Bogota";
        this.locationData = apiService.findCity(this.city);
    }

    /**
     * Tests that the findCity method returns data for the test city.
     */
    @Test
    void findCityFound() {
        assertNotNull(this.locationData);
    }

    /**
     * Tests that the findCity method returns no data for a non-existent city.
     */
    @Test
    void findCityNotFound() {
        LocationData[] locationData2 = apiService.findCity("doesNotExist");
        assertEquals(0, locationData2.length);
    }

    /**
     * Tests that the currentWeather method returns data for the test city.
     */
    @Test
    void currentWeather() {
        assertNotNull(apiService.currentWeather(this.city, this.locationData[0]));
    }

    /**
     * Tests that the forecast method returns data for the test city.
     */
    @Test
    void forecast() {
        assertNotNull(apiService.forecast(this.city, this.locationData[0]));
    }

    /**
     * Tests that the airPollution method returns data for the test city.
     */
    @Test
    void airPollution() {
        assertNotNull(apiService.airPollution(this.city, this.locationData[0]));
    }
}