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

    @Cacheable(value = "findCity", key = "#city")
    public LocationData[] findCity(String city) {
        return this.restTemplate.getForObject(this.urlToSearchCity(city), LocationData[].class);
//        log.info("Datos de la ciudad {} obtenidos de la api", locationData);
    }

    @Cacheable(value = "currentWeather", key = "#city")
    public CurrentWeather currentWeather(String city, LocationData locationData) {
        return this.restTemplate.getForObject(this.urlToSearchData(locationData, "weather"), CurrentWeather.class);
//        log.info("Datos de currentWeather {} obtenidos de la api", currentWeather);
    }

    @Cacheable(value = "forecast", key = "#city")
    public Forecast forecast(String city, LocationData locationData) {
        return this.restTemplate.getForObject(this.urlToSearchData(locationData, "forecast"), Forecast.class);
//        log.info("Datos de forecast {} obtenidos de la api", forecast);
    }

    @Cacheable(value = "airPollution", key = "#city")
    public AirPollution airPollution(String city, LocationData locationData) {
        return this.restTemplate.getForObject(this.urlToSearchData(locationData, "air_pollution"), AirPollution.class);
//        log.info("Datos de airPollution {} obtenidos de la api", airPollution);
    }

    private String urlToSearchCity(String city) {
        return String.format(BASE_URL_GEO.concat("direct?q=%s").concat("&appid=%s").concat("&limit=1"), city, API_ID);
    }

    private String urlToSearchData(LocationData locationData, String option) {
        return String.format(BASE_URL_DATA.concat("%s?lat=%.10f").concat("&lon=%.10f").concat("&appid=%s"), option, locationData.getLat(), locationData.getLon(), API_ID);
    }
}
