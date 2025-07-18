package com.example.demo.service;

import com.example.demo.dto.RatingRequestDTO;
import com.example.demo.dto.RatingResponseDTO;
import com.example.demo.model.Rating;
import com.example.demo.model.Restaurant;
import com.example.demo.model.Visitor;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final VisitorRepository visitorRepository;
    private final RestaurantRepository restaurantRepository;

    public RatingResponseDTO create(RatingRequestDTO dto) {
        Visitor visitor = visitorRepository.findById(dto.getVisitorId())
                .orElseThrow(() -> new NoSuchElementException("Visitor not found"));
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new NoSuchElementException("Restaurant not found"));

        Rating rating = new Rating();
        rating.setVisitor(visitor);
        rating.setRestaurant(restaurant);
        rating.setScore(dto.getScore());
        rating.setComment(dto.getComment());

        ratingRepository.save(rating);
        updateRestaurantRating(restaurant.getId());
        return toDTO(rating);
    }

    public List<RatingResponseDTO> getAll() {
        return ratingRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RatingResponseDTO update(Long visitorId, Long restaurantId, RatingRequestDTO dto) {
        Rating rating = ratingRepository.findByVisitorIdAndRestaurantId(visitorId, restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Rating not found"));

        rating.setScore(dto.getScore());
        rating.setComment(dto.getComment());
        ratingRepository.save(rating);
        updateRestaurantRating(restaurantId);
        return toDTO(rating);
    }

    public void delete(Long visitorId, Long restaurantId) {
        Rating rating = ratingRepository.findByVisitorIdAndRestaurantId(visitorId, restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Rating not found"));

        ratingRepository.delete(rating);
        updateRestaurantRating(restaurantId);
    }

    private RatingResponseDTO toDTO(Rating rating) {
        return new RatingResponseDTO(
                rating.getVisitor().getId(),
                rating.getRestaurant().getId(),
                rating.getScore(),
                rating.getComment()
        );
    }

    private void updateRestaurantRating(Long restaurantId) {
        List<Rating> ratings = ratingRepository.findAllByRestaurantId(restaurantId);
        if (!ratings.isEmpty()) {
            double avg = ratings.stream()
                    .mapToInt(Rating::getScore)
                    .average()
                    .orElse(0);
            BigDecimal avgRounded = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);
            restaurantRepository.findById(restaurantId)
                    .ifPresent(r -> {
                        r.setAverageRating(avgRounded);
                        restaurantRepository.save(r);
                    });
        }
    }
}
