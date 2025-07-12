package com.example.demo.config;

import com.example.demo.model.*;
import com.example.demo.service.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final RatingService ratingService;

    @PostConstruct
    public void init() {
        visitorService.save(new Visitor(1L, "Иван", 25, "М"));
        visitorService.save(new Visitor(2L, null, 30, "Ж"));

        restaurantService.save(new Restaurant(
                1L,
                "Bella Italia",
                "Итальянская кухня",
                CuisineType.ITALIAN,
                new BigDecimal("1500"),
                BigDecimal.ZERO
        ));

        restaurantService.save(new Restaurant(
                2L,
                "Asia Wok",
                "",
                CuisineType.CHINESE,
                new BigDecimal("900"),
                BigDecimal.ZERO
        ));

        ratingService.save(new Rating(1L, 1L, 5, "Отлично!"));
        ratingService.save(new Rating(2L, 1L, 4, ""));
        ratingService.save(new Rating(1L, 2L, 3, "Нормально"));


        System.out.println("\nВсе посетители:");
        visitorService.findAll().forEach(System.out::println);

        System.out.println("\nВсе рестораны:");
        restaurantService.findAll().forEach(System.out::println);

        System.out.println("\nВсе оценки:");
        ratingService.findAll().forEach(System.out::println);
    }

}
