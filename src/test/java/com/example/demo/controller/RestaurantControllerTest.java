package com.example.demo.controller;

import com.example.demo.dto.RestaurantRequestDTO;
import com.example.demo.model.CuisineType;
import com.example.demo.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createRestaurant_shouldReturnOk() throws Exception {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "Test Restaurant",
                "Test Description",
                CuisineType.ITALIAN,
                new BigDecimal("1200")
        );

        mockMvc.perform(post("/api/restaurants")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void getRestaurants_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk());
    }

    @Test
    void updateRestaurant_shouldReturnOk() throws Exception {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "Updated Name",
                "Updated Description",
                CuisineType.CHINESE,
                new BigDecimal("999")
        );

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRestaurant_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isOk());
    }
}
