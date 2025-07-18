package com.example.demo.service;

import com.example.demo.dto.RestaurantRequestDTO;
import com.example.demo.dto.RestaurantResponseDTO;
import com.example.demo.model.Restaurant;
import com.example.demo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantResponseDTO create(RestaurantRequestDTO dto) {
        Restaurant restaurant = new Restaurant(null, dto.getName(), dto.getDescription(), dto.getCuisineType(), dto.getAverageBill(), BigDecimal.ZERO);
        Restaurant saved = repository.save(restaurant);
        return toDTO(saved);
    }

    public List<RestaurantResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RestaurantResponseDTO update(Long id, RestaurantRequestDTO dto) {
        Restaurant updated = new Restaurant(id, dto.getName(), dto.getDescription(), dto.getCuisineType(), dto.getAverageBill(), BigDecimal.ZERO);
        return toDTO(repository.save(updated));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private RestaurantResponseDTO toDTO(Restaurant restaurant) {
        return new RestaurantResponseDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getCuisineType(),
                restaurant.getAverageBill(),
                restaurant.getAverageRating()
        );
    }
}
