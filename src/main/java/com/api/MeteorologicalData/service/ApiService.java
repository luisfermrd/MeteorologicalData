package com.api.MeteorologicalData.service;

import com.api.MeteorologicalData.dto.Message;
import com.api.MeteorologicalData.dto.airPollution.AirPollution;
import com.api.MeteorologicalData.dto.forecast.Forecast;
import com.api.MeteorologicalData.dto.location.LocationData;
import com.api.MeteorologicalData.dto.weather.CurrentWeather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private RestTemplate restTemplate;

    public ApiService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }
    public LocationData[] findCity(String city){
        return this.restTemplate.getForObject(this.urlToSearchCity(city), LocationData[].class);
    }

    public ResponseEntity<?> currentWeather(String city){
        LocationData[] cityData = findCity(city);
        if (cityData == null || cityData.length == 0){
            return new ResponseEntity<Message>(Message.builder()
                                                        .message("City not found")
                                                        .build(), HttpStatus.NOT_FOUND);
        }
        CurrentWeather currentWeather = this.restTemplate.getForObject(this.urlToSearchData(cityData[0], "weather"), CurrentWeather.class);
        return new ResponseEntity<CurrentWeather>(currentWeather, HttpStatus.OK);
    }

    public ResponseEntity<?> forecast(String city){
        LocationData[] cityData = findCity(city);
        if (cityData == null || cityData.length == 0){
            return new ResponseEntity<Message>(Message.builder()
                    .message("City not found")
                    .build(), HttpStatus.NOT_FOUND);
        }
        Forecast forecast = this.restTemplate.getForObject(this.urlToSearchData(cityData[0], "forecast"), Forecast.class);
        return new ResponseEntity<Forecast>(forecast, HttpStatus.OK);
    }

    public ResponseEntity<?> airPollution(String city){
        LocationData[] cityData = findCity(city);
        if (cityData == null || cityData.length == 0){
            return new ResponseEntity<Message>(Message.builder()
                    .message("City not found")
                    .build(), HttpStatus.NOT_FOUND);
        }
        AirPollution airPollution = this.restTemplate.getForObject(this.urlToSearchData(cityData[0], "air_pollution"), AirPollution.class);
        return new ResponseEntity<AirPollution>(airPollution, HttpStatus.OK);
    }

    private String urlToSearchCity(String city) {
        return String.format(BASE_URL_GEO.concat("direct?q=%s").concat("&appid=%s").concat("&limit=1"), city, API_ID);
    }

    private String urlToSearchData(LocationData locationData, String option) {
        return String.format(BASE_URL_DATA.concat("%s?lat=%.10f").concat("&lon=%.10f").concat("&appid=%s"), option, locationData.getLat(), locationData.getLon(), API_ID);
    }
//    private String urlToSearchCurrentWeather(LocationData locationData) {
//        return String.format(BASE_URL_DATA.concat("weather?lat=%.10f").concat("&lon=%.10f").concat("&appid=%s"), locationData.getLat(), locationData.getLon(), API_ID);
//    }
//
//    private String urlToSearchForecastForTheLastFiveDays(LocationData locationData) {
//        return String.format(BASE_URL_DATA.concat("forecast?lat=%.10f").concat("&lon=%.10f").concat("&appid=%s"), locationData.getLat(), locationData.getLon(), API_ID);
//    }
}
