package com.example.demo.repository;

import com.example.demo.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Repository
public class RestaurantRepository {
    private final List<Restaurant> restaurants = new ArrayList<>();

    public void save(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void remove(Restaurant restaurant) {
        restaurants.remove(restaurant);
    }

    public void removeIf(Predicate<Restaurant> predicate) {
        restaurants.removeIf(predicate);
    }

    public List<Restaurant> findAll() {
        return restaurants;
    }
}
