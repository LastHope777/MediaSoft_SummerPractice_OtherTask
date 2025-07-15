package com.example.demo.dto;

import lombok.Value;

@Value
public class RatingRequestDTO {
    Long visitorId;
    Long restaurantId;
    int score;
    String comment;
}
