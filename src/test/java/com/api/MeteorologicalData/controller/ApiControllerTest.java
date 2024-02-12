package com.api.MeteorologicalData.controller;

import com.api.MeteorologicalData.repository.AuditRepository;
import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiControllerTest {

    private final static String BASE_URL = "/api";
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuditRepository auditRepository;

    private final String city = "Bogota";

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        User user = new User("User", "User@mail.com", "Username", passwordEncoder.encode("12345"));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    @Order(1)
    @WithMockUser
    void location() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL.concat("/geo/").concat(this.city))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @Order(2)
    @WithMockUser(value = "Username")
    void currentWeather() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL.concat("/weather/").concat(this.city))
                )
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @Order(3)
    @WithMockUser(value = "Username")
    void currentWeatherCityNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL.concat("/weather/").concat("CityNotFound"))
                )
                .andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    @Order(4)
    @WithMockUser(value = "Username")
    void forecast() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL.concat("/forecast/").concat(this.city))
                )
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @Order(5)
    @WithMockUser(value = "Username")
    void forecastCityNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL.concat("/forecast/").concat("CityNotFound"))
                )
                .andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    @Order(6)
    @WithMockUser(value = "Username")
    void airPollution() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL.concat("/air-pollution/").concat(this.city))
                )
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @Order(7)
    @WithMockUser(value = "Username")
    void airPollutionCityNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL.concat("/air-pollution/").concat("CityNotFound"))
                )
                .andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    @Order(8)
    @WithMockUser
    void exceedingTheUserRequestsLimitInLocation() throws Exception {
        MvcResult result = null;
        for (int i = 0; i <= 100; i++) {
            result = mockMvc.perform(MockMvcRequestBuilders
                            .get(BASE_URL.concat("/geo/").concat(this.city))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andReturn();
        }
        assertEquals(429, result.getResponse().getStatus());
    }

    @Test
    @Order(9)
    @WithMockUser(value = "Username")
    void exceedingTheUserRequestsLimitInCurrentWeather() throws Exception {
        MvcResult result = null;
        for (int i = 0; i <= 100; i++) {
            result = mockMvc.perform(MockMvcRequestBuilders
                            .get(BASE_URL.concat("/weather/").concat(this.city))
                    )
                    .andReturn();
        }
    }

    @Test
    @Order(10)
    @WithMockUser(value = "Username")
    void exceedingTheUserRequestsLimitInForecast() throws Exception {
        MvcResult result = null;
        for (int i = 0; i <= 100; i++) {
            result = mockMvc.perform(MockMvcRequestBuilders
                            .get(BASE_URL.concat("/forecast/").concat(this.city))
                    )
                    .andReturn();
        }
        assertEquals(429, result.getResponse().getStatus());
    }

    @Test
    @Order(11)
    @WithMockUser(value = "Username")
    void exceedingTheUserRequestsLimitInAirPollution() throws Exception {
        MvcResult result = null;
        for (int i = 0; i <= 100; i++) {
            result = mockMvc.perform(MockMvcRequestBuilders
                            .get(BASE_URL.concat("/air-pollution/").concat(this.city))
                    )
                    .andReturn();
        }
        assertEquals(429, result.getResponse().getStatus());
    }


    @Test
    @Order(12)
    @WithMockUser
    void exceedingTheServerRequestsLimitInLocation() throws Exception {
        MvcResult result = null;
        for (int i = 0; i <= 1000; i++) {
            result = mockMvc.perform(MockMvcRequestBuilders
                            .get(BASE_URL.concat("/geo/").concat(this.city))
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andReturn();
        }
        assertEquals(429, result.getResponse().getStatus());
    }

    @Test
    @Order(13)
    @WithMockUser(value = "Username")
    void exceedingTheServerRequestsLimitInCurrentWeather() throws Exception {
        MvcResult result = null;
        for (int i = 0; i <= 1000; i++) {
            result = mockMvc.perform(MockMvcRequestBuilders
                            .get(BASE_URL.concat("/weather/").concat(this.city))
                    )
                    .andReturn();
        }
    }

    @Test
    @Order(14)
    @WithMockUser(value = "Username")
    void exceedingTheServerRequestsLimitInForecast() throws Exception {
        MvcResult result = null;
        for (int i = 0; i <= 1000; i++) {
            result = mockMvc.perform(MockMvcRequestBuilders
                            .get(BASE_URL.concat("/forecast/").concat(this.city))
                    )
                    .andReturn();
        }
        assertEquals(429, result.getResponse().getStatus());
    }

    @Test
    @Order(15)
    @WithMockUser(value = "Username")
    void exceedingTheServerRequestsLimitInaAirPollution() throws Exception {
        MvcResult result = null;
        for (int i = 0; i <= 1000; i++) {
            result = mockMvc.perform(MockMvcRequestBuilders
                            .get(BASE_URL.concat("/air-pollution/").concat(this.city))
                    )
                    .andReturn();
        }
        assertEquals(429, result.getResponse().getStatus());
    }

}