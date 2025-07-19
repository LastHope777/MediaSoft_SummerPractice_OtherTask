package com.example.demo.service;

import com.example.demo.dto.RatingRequestDTO;
import com.example.demo.model.Rating;
import com.example.demo.model.Restaurant;
import com.example.demo.model.Visitor;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.repository.VisitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private VisitorRepository visitorRepository;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveRating() {
        // given
        RatingRequestDTO dto = new RatingRequestDTO(1L, 2L, 5, "Хорошо");
        Visitor visitor = new Visitor(1L, "Иван", 25, "М");
        Restaurant restaurant = new Restaurant(2L, "Tokyo", "Японская", null, null, null);

        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));
        when(restaurantRepository.findById(2L)).thenReturn(Optional.of(restaurant));

        // when
        ratingService.create(dto);

        // then
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void delete_shouldDeleteRatingIfExists() {
        // given
        Visitor visitor = new Visitor(1L, "Иван", 25, "М");
        Restaurant restaurant = new Restaurant(2L, "Tokyo", "Японская", null, null, null);
        Rating rating = new Rating(10L, visitor, restaurant, 4, "Ок");

        when(ratingRepository.findByVisitorIdAndRestaurantId(1L, 2L)).thenReturn(Optional.of(rating));

        // when
        ratingService.delete(1L, 2L);

        // then
        verify(ratingRepository).delete(rating);
    }
}
