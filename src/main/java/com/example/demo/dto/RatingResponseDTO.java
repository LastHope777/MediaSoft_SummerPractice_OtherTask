package com.example.demo.dto;

import lombok.Value;

@Value
public class RatingResponseDTO {
    Long visitorId;
    Long restaurantId;
    int score;
    String comment;
}
