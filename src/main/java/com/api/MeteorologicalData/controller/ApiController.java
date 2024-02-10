package com.api.MeteorologicalData.controller;

import com.api.MeteorologicalData.dto.Message;
import com.api.MeteorologicalData.dto.airPollution.AirPollution;
import com.api.MeteorologicalData.dto.forecast.Forecast;
import com.api.MeteorologicalData.dto.location.LocationData;
import com.api.MeteorologicalData.dto.weather.CurrentWeather;
import com.api.MeteorologicalData.entity.Audit;
import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.service.AuthService;
import com.api.MeteorologicalData.security.service.UserService;
import com.api.MeteorologicalData.service.ApiService;
import com.api.MeteorologicalData.service.AuditService;
import com.api.MeteorologicalData.util.ApiType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;
    private final AuditService auditService;
    private final UserService userService;
    private final CacheManager cacheManager;
    private Authentication authentication;
    private final AuthService authService;
    private final Bucket bucket;

    public ApiController(ApiService apiService, AuditService auditService, UserService userService, CacheManager cacheManager, AuthService authService, Bucket bucket) {
        this.apiService = apiService;
        this.auditService = auditService;
        this.userService = userService;
        this.cacheManager = cacheManager;
        this.authService = authService;
        this.bucket = bucket;
    }

    @GetMapping("/geo/{city}")
    public ResponseEntity<?> location(@PathVariable("city") String city) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (this.limit(username)) {
            if (this.bucket.tryConsume(1)) {
                return new ResponseEntity<LocationData[]>(apiService.findCity(city), HttpStatus.OK);
            }
            return new ResponseEntity<Message>(Message.builder().message("Request limit on server exceeded. Try it again later.").build(), HttpStatus.TOO_MANY_REQUESTS);
        }
        return new ResponseEntity<Message>(Message.builder().message("Request limit exceeded, try again later.").build(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/weather/{city}")
    public ResponseEntity<?> currentWeather(@PathVariable("city") String city) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (this.limit(username)) {
            if (this.bucket.tryConsume(1)) {
                LocationData[] cityData = apiService.findCity(city);
                if (cityData == null || cityData.length == 0) {
                    Message message = Message.builder().message("City {" + city + "} not found").build();
                    this.performAudit(message, ApiType.CURRENT_WEATHER.getDisplayName(), username);
                    return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
                }
                CurrentWeather currentWeather = apiService.currentWeather(city, cityData[0]);
                this.performAudit(currentWeather, ApiType.CURRENT_WEATHER.getDisplayName(), username);
                return new ResponseEntity<CurrentWeather>(currentWeather, HttpStatus.OK);
            }
            return new ResponseEntity<Message>(Message.builder().message("Request limit on server exceeded. Try it again later.").build(), HttpStatus.TOO_MANY_REQUESTS);
        }
        return new ResponseEntity<Message>(Message.builder().message("Request limit exceeded, try again later.").build(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/forecast/{city}")
    public ResponseEntity<?> forecast(@PathVariable("city") String city) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (this.limit(username)) {
            if (this.bucket.tryConsume(1)) {
                LocationData[] cityData = apiService.findCity(city);
                if (cityData == null || cityData.length == 0) {
                    Message message = Message.builder().message("City {" + city + "} not found").build();
                    this.performAudit(message, ApiType.FORECAST.getDisplayName(), username);
                    return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
                }
                Forecast forecast = apiService.forecast(city, cityData[0]);
                this.performAudit(forecast, ApiType.FORECAST.getDisplayName(), username);
                return new ResponseEntity<Forecast>(forecast, HttpStatus.OK);
            }
            return new ResponseEntity<Message>(Message.builder().message("Request limit on server exceeded. Try it again later.").build(), HttpStatus.TOO_MANY_REQUESTS);
        }
        return new ResponseEntity<Message>(Message.builder().message("Request limit exceeded, try again later.").build(), HttpStatus.TOO_MANY_REQUESTS);

    }

    @GetMapping("/air-pollution/{city}")
    public ResponseEntity<?> airPollution(@PathVariable("city") String city) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (this.limit(username)) {
            if (this.bucket.tryConsume(1)) {
                LocationData[] cityData = apiService.findCity(city);
                if (cityData == null || cityData.length == 0) {
                    Message message = Message.builder().message("City {" + city + "} not found").build();
                    this.performAudit(message, ApiType.AIR_POLLUTION.getDisplayName(), username);
                    return new ResponseEntity<Message>(message, HttpStatus.NOT_FOUND);
                }
                AirPollution airPollution = apiService.airPollution(city, cityData[0]);
                this.performAudit(airPollution, ApiType.AIR_POLLUTION.getDisplayName(), username);
                return new ResponseEntity<AirPollution>(airPollution, HttpStatus.OK);
            }
            return new ResponseEntity<Message>(Message.builder().message("Request limit on server exceeded. Try it again later.").build(), HttpStatus.TOO_MANY_REQUESTS);
        }
        return new ResponseEntity<Message>(Message.builder().message("Request limit exceeded, try again later.").build(), HttpStatus.TOO_MANY_REQUESTS);

    }

    private void performAudit(Object object, String request, String username) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = this.userService.getByUsername(username).get();
        Audit audit = new Audit();
        audit.setUser(user);
        audit.setRequests(request);
        audit.setDateRequets(new Date());
        try {
            audit.setResponts(objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            audit.setResponts(object.toString());
        }
        auditService.save(audit);
    }

    private boolean limit(String username) {
        Cache cache = cacheManager.getCache("limiterApi");
        Cache.ValueWrapper valueWrapper = cache.get(username);
        if (valueWrapper != null) {
            if (valueWrapper.get() instanceof Bucket bucket1) {
                if (bucket1.tryConsume(1)) {
                    cache.put(username, bucket1);
                    return true;
                }
            }
        } else {
            authService.limiterApi(username);
            return limit(username);
        }

        return false;
    }
}
