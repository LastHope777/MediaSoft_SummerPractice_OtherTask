package com.example.demo.service;

import com.example.demo.dto.RestaurantRequestDTO;
import com.example.demo.dto.RestaurantResponseDTO;
import com.example.demo.model.Restaurant;
import com.example.demo.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public RestaurantResponseDTO create(RestaurantRequestDTO dto) {
        Restaurant restaurant = new Restaurant(
                idGenerator.getAndIncrement(),
                dto.getName(),
                dto.getDescription(),
                dto.getCuisineType(),
                dto.getAverageBill(),
                BigDecimal.ZERO
        );
        repository.save(restaurant);
        return toDTO(restaurant);
    }

    public List<RestaurantResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RestaurantResponseDTO update(Long id, RestaurantRequestDTO dto) {
        repository.removeIf(r -> r.getId().equals(id));
        Restaurant updated = new Restaurant(
                id,
                dto.getName(),
                dto.getDescription(),
                dto.getCuisineType(),
                dto.getAverageBill(),
                BigDecimal.ZERO
        );
        repository.save(updated);
        return toDTO(updated);
    }

    public void delete(Long id) {
        repository.removeIf(r -> r.getId().equals(id));
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
