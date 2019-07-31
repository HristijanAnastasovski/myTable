package com.finki.MyTable.service;

import com.finki.MyTable.model.MenuItem;
import com.finki.MyTable.model.Restaurant;
import com.finki.MyTable.model.exception.*;

import java.util.List;

public interface RestaurantManagementService {
    Restaurant registerNewRestaurant(String restaurantName, String restaurantAddress, String restaurantEmail, String restaurantPassword, String restaurantLogo, Long billingAccountNumber) throws RestaurantAlreadyExistsException, EmailAlreadyInUseException;
    Restaurant loginRestaurant(String email, String password) throws EmailNotFoundException, IncorrectPasswordException, AccountNotActiveException;
    Restaurant changeRestaurantInformation(Long id, String restaurantName, String restaurantAddress, String restaurantLogo, Long billingAccountNumber) throws UnknownRestaurantException, RestaurantAlreadyExistsException;
    Restaurant changeRestaurantPassword(Long id, String newPassword) throws UnknownRestaurantException, NoDifferenceInPasswordsException;
    void removeRestaurant(Long id) throws UnknownRestaurantException;
    void addItemToRestaurantMenu(String itemName, String description, double price, int quantity, Long restaurantId, String menuImage) throws ItemWithThatNameAlreadyExistsInMenuException, UnknownRestaurantException;
    void removeItemFromRestaurantMenu(Long itemId) throws ItemNotFoundException;
    void editItemFromRestaurantMenu(Long itemId, String itemName, String description, double price, String menuImage) throws ItemNotFoundException;
    void addQuantityToMenuItem(Long itemId, int quantity) throws ItemNotFoundException;
    Restaurant getRestaurant(Long restaurantId) throws UnknownRestaurantException;
    List<Restaurant> getAllRestaurants();
    List<MenuItem> getRestaurantMenu(Long restaurantId) throws UnknownRestaurantException;

}
