package com.api.MeteorologicalData.service;

import com.api.MeteorologicalData.dto.airPollution.AirPollution;
import com.api.MeteorologicalData.dto.forecast.Forecast;
import com.api.MeteorologicalData.dto.location.LocationData;
import com.api.MeteorologicalData.dto.weather.CurrentWeather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service that makes calls to the OpenWeather API to obtain geographic and meteorological data.
 */
@Slf4j
@Service
public class ApiService {

    @Value("${openweather.geolocation.base-url}")
    private String BASE_URL_GEO;

    @Value("${openweather.meteorologial-data.base-url}")
    private String BASE_URL_DATA;

    @Value("${openweather.api-id}")
    private String API_ID;

    private final RestTemplate restTemplate;

    public ApiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Method that searches for the city using the OpenWeather geolocation API.
     *
     * @param city The name of the city to search for.
     * @return An array of LocationData objects that represent the geographic information of the city.
     */
    @Cacheable(value = "findCity", key = "#city")
    public LocationData[] findCity(String city) {
        return this.restTemplate.getForObject(this.urlToSearchCity(city), LocationData[].class);
    }

    /**
     * Method that obtains current weather data using the OpenWeather API.
     *
     * @param city         The name of the city.
     * @param locationData The geographic data of the city.
     * @return A CurrentWeather object that represents the current weather information.
     */
    @Cacheable(value = "currentWeather", key = "#city")
    public CurrentWeather currentWeather(String city, LocationData locationData) {
        return this.restTemplate.getForObject(this.urlToSearchData(locationData, "weather"), CurrentWeather.class);
    }

    /**
     * Method that obtains weather forecasts using the OpenWeather API.
     *
     * @param city         The name of the city.
     * @param locationData The geographic data of the city.
     * @return A Forecast object that represents the weather forecast.
     */
    @Cacheable(value = "forecast", key = "#city")
    public Forecast forecast(String city, LocationData locationData) {
        return this.restTemplate.getForObject(this.urlToSearchData(locationData, "forecast"), Forecast.class);
    }

    /**
     * Method that obtains air pollution data using the OpenWeather API.
     *
     * @param city         The name of the city.
     * @param locationData The geographic data of the city.
     * @return An AirPollution object that represents air pollution information.
     */
    @Cacheable(value = "airPollution", key = "#city")
    public AirPollution airPollution(String city, LocationData locationData) {
        return this.restTemplate.getForObject(this.urlToSearchData(locationData, "air_pollution"), AirPollution.class);
    }

    /**
     * Private method that constructs the URL to search for geographic information about a city.
     *
     * @param city The name of the city.
     * @return The URL constructed for the geographic search.
     */
    private String urlToSearchCity(String city) {
        return String.format(BASE_URL_GEO.concat("direct?q=%s").concat("&appid=%s").concat("&limit=1"), city, API_ID);
    }

    /**
     * Private method that constructs the URL to search for weather data using geographic information.
     *
     * @param locationData The geographic data of the city.
     * @param option       The API option to get specific data (weather, forecast, air_pollution).
     * @return The constructed URL for the weather data search.
     */
    private String urlToSearchData(LocationData locationData, String option) {
        return String.format(BASE_URL_DATA.concat("%s?lat=%.10f").concat("&lon=%.10f").concat("&appid=%s"), option, locationData.getLat(), locationData.getLon(), API_ID);
    }
}
