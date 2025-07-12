package com.example.demo.service;

import com.example.demo.model.Rating;
import com.example.demo.model.Restaurant;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;

    public void save(Rating rating) {
        ratingRepository.save(rating);
        updateRestaurantAverageRating(rating.getRestaurantId());
    }

    public void remove(Rating rating) {
        ratingRepository.remove(rating);
        updateRestaurantAverageRating(rating.getRestaurantId());
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    private void updateRestaurantAverageRating(Long restaurantId) {
        List<Rating> ratings = ratingRepository.findByRestaurantId(restaurantId);
        if (!ratings.isEmpty()) {
            double avg = ratings.stream()
                    .mapToInt(Rating::getScore)
                    .average()
                    .orElse(0.0);
            BigDecimal avgRounded = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);

            restaurantRepository.findAll().stream()
                    .filter(r -> r.getId().equals(restaurantId))
                    .findFirst()
                    .ifPresent(r -> r.setAverageRating(avgRounded));
        }
    }
}
