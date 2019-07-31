package com.finki.MyTable.model.factory;

import com.finki.MyTable.model.Restaurant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

public class RestaurantFactory {
    public static Restaurant create(String name, String address, String restaurantEmail, String restaurantPassword, String restaurantLogo, Long billingAccountNumber){
        Restaurant restaurant = new Restaurant();
        restaurant.restaurantName= name;
        restaurant.restaurantAddress = address;
        restaurant.restaurantEmail=restaurantEmail;
        restaurant.isActive = true;
        restaurant.restaurantLogo = restaurantLogo;
        restaurant.billingAccountNumber = billingAccountNumber;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        restaurant.restaurantPassword = passwordEncoder.encode(restaurantPassword);

        return restaurant;
    }
}
