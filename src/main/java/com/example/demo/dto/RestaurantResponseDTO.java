package com.example.demo.dto;

import com.example.demo.model.CuisineType;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class RestaurantResponseDTO {
    Long id;
    String name;
    String description;
    CuisineType cuisineType;
    BigDecimal averageBill;
    BigDecimal averageRating;
}
