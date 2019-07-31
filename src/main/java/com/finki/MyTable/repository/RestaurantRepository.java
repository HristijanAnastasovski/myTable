package com.finki.MyTable.repository;

import com.finki.MyTable.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Optional<Restaurant> findByRestaurantNameAndRestaurantAddress(String restaurantName, String restaurantAddress);
    Optional<Restaurant> findByRestaurantEmail(String email);
}
