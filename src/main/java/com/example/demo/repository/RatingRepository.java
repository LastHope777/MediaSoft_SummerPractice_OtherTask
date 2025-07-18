package com.example.demo.repository;

import com.example.demo.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByVisitorIdAndRestaurantId(Long visitorId, Long restaurantId);
    List<Rating> findAllByRestaurantId(Long restaurantId);
}
