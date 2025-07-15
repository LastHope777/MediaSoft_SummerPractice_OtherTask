package com.example.demo.controller;

import com.example.demo.dto.RatingRequestDTO;
import com.example.demo.dto.RatingResponseDTO;
import com.example.demo.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService service;

    @PostMapping
    public RatingResponseDTO create(@RequestBody RatingRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<RatingResponseDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{visitorId}/{restaurantId}")
    public RatingResponseDTO update(
            @PathVariable Long visitorId,
            @PathVariable Long restaurantId,
            @RequestBody RatingRequestDTO dto
    ) {
        return service.update(visitorId, restaurantId, dto);
    }

    @DeleteMapping("/{visitorId}/{restaurantId}")
    public void delete(@PathVariable Long visitorId, @PathVariable Long restaurantId) {
        service.delete(visitorId, restaurantId);
    }
}
