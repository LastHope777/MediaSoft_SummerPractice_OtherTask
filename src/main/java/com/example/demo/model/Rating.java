package com.example.demo.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private Long visitorId;
    private Long restaurantId;
    private int score;
    private String comment;
}
