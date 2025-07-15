package com.example.demo.service;

import com.example.demo.dto.RatingRequestDTO;
import com.example.demo.dto.RatingResponseDTO;
import com.example.demo.model.Rating;
import com.example.demo.model.Restaurant;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;

    public RatingResponseDTO create(RatingRequestDTO dto) {
        Rating rating = new Rating(dto.getVisitorId(), dto.getRestaurantId(), dto.getScore(), dto.getComment());
        ratingRepository.save(rating);
        updateRestaurantRating(dto.getRestaurantId());
        return toDTO(rating);
    }

    public List<RatingResponseDTO> getAll() {
        return ratingRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RatingResponseDTO update(Long visitorId, Long restaurantId, RatingRequestDTO dto) {
        ratingRepository.removeIf(r -> r.getVisitorId().equals(visitorId) && r.getRestaurantId().equals(restaurantId));
        Rating updated = new Rating(dto.getVisitorId(), dto.getRestaurantId(), dto.getScore(), dto.getComment());
        ratingRepository.save(updated);
        updateRestaurantRating(restaurantId);
        return toDTO(updated);
    }

    public void delete(Long visitorId, Long restaurantId) {
        ratingRepository.removeIf(r -> r.getVisitorId().equals(visitorId) && r.getRestaurantId().equals(restaurantId));
        updateRestaurantRating(restaurantId);
    }

    private RatingResponseDTO toDTO(Rating rating) {
        return new RatingResponseDTO(
                rating.getVisitorId(),
                rating.getRestaurantId(),
                rating.getScore(),
                rating.getComment()
        );
    }

    private void updateRestaurantRating(Long restaurantId) {
        List<Rating> ratings = ratingRepository.findByRestaurantId(restaurantId);
        if (!ratings.isEmpty()) {
            double avg = ratings.stream().mapToInt(Rating::getScore).average().orElse(0);
            BigDecimal avgRounded = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);
            restaurantRepository.findAll().stream()
                    .filter(r -> r.getId().equals(restaurantId))
                    .findFirst()
                    .ifPresent(r -> r.setAverageRating(avgRounded));
        }
    }
}
