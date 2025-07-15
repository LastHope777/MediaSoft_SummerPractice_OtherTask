package com.example.demo.repository;

import com.example.demo.model.Rating;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class RatingRepository {
    private final List<Rating> ratings = new ArrayList<>();

    public void save(Rating rating) {
        ratings.add(rating);
    }

    public void remove(Rating rating) {
        ratings.remove(rating);
    }

    public void removeIf(Predicate<Rating> predicate) {
        ratings.removeIf(predicate);
    }

    public List<Rating> findAll() {
        return ratings;
    }

    public Optional<Rating> findById(Long visitorId, Long restaurantId) {
        return ratings.stream()
                .filter(r -> r.getVisitorId().equals(visitorId) && r.getRestaurantId().equals(restaurantId))
                .findFirst();
    }

    public List<Rating> findByRestaurantId(Long restaurantId) {
        return ratings.stream()
                .filter(r -> r.getRestaurantId().equals(restaurantId))
                .collect(Collectors.toList());
    }
}
