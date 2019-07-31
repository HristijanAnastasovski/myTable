package com.finki.MyTable.service.implementation;

import com.finki.MyTable.model.MenuItem;
import com.finki.MyTable.model.Restaurant;
import com.finki.MyTable.model.exception.*;
import com.finki.MyTable.model.factory.MenuItemFactory;
import com.finki.MyTable.model.factory.RestaurantFactory;
import com.finki.MyTable.repository.MenuItemRepository;
import com.finki.MyTable.repository.RestaurantRepository;
import com.finki.MyTable.service.RestaurantManagementService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantManagementServiceImpl implements RestaurantManagementService {
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RestaurantManagementServiceImpl(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public Restaurant registerNewRestaurant(String restaurantName, String restaurantAddress, String restaurantEmail, String restaurantPassword, String restaurantLogo, Long billingAccountNumber) throws RestaurantAlreadyExistsException, EmailAlreadyInUseException {
        if(restaurantRepository.findByRestaurantNameAndRestaurantAddress(restaurantName,restaurantAddress).isPresent())
            throw new RestaurantAlreadyExistsException();
        if(restaurantRepository.findByRestaurantEmail(restaurantEmail).isPresent())
            throw new EmailAlreadyInUseException();
        Restaurant restaurant = RestaurantFactory.create(restaurantName, restaurantAddress, restaurantEmail, restaurantPassword, restaurantLogo, billingAccountNumber);

        return restaurantRepository.save(restaurant);

    }



    @Override
    public Restaurant changeRestaurantInformation(Long id, String restaurantName, String restaurantAddress, String restaurantLogo, Long billingAccountNumber) throws UnknownRestaurantException, RestaurantAlreadyExistsException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(UnknownRestaurantException::new);
        if(restaurantRepository.findByRestaurantNameAndRestaurantAddress(restaurantName,restaurantAddress).isPresent() && (!restaurant.restaurantName.equals(restaurantName) || !restaurant.restaurantAddress.equals(restaurantAddress)))
            throw new RestaurantAlreadyExistsException();
        restaurant.restaurantName = restaurantName;
        restaurant.restaurantAddress = restaurantAddress;
        restaurant.restaurantLogo = restaurantLogo;
        restaurant.billingAccountNumber = billingAccountNumber;
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant changeRestaurantPassword(Long id, String newPassword) throws UnknownRestaurantException, NoDifferenceInPasswordsException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(UnknownRestaurantException::new);
        String password = passwordEncoder.encode(newPassword);
        if(restaurant.restaurantPassword.equals(password))
            throw new NoDifferenceInPasswordsException();
        restaurant.restaurantPassword = password;
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void removeRestaurant(Long id) throws UnknownRestaurantException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(UnknownRestaurantException::new);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public void addItemToRestaurantMenu(String itemName, String description, double price, int quantity, Long restaurantId, String menuImage) throws ItemWithThatNameAlreadyExistsInMenuException, UnknownRestaurantException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(UnknownRestaurantException::new);
        if(restaurant.containsMenuItem(itemName,menuItemRepository.findAllByRestaurant(restaurant)))
            throw new ItemWithThatNameAlreadyExistsInMenuException();
        MenuItem menuItem = MenuItemFactory.create(itemName,description,price,quantity,restaurant,menuImage);
        menuItemRepository.save(menuItem);

    }



    @Override
    public void removeItemFromRestaurantMenu(Long itemId) throws ItemNotFoundException {
        MenuItem menuItem = menuItemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        menuItemRepository.delete(menuItem);

    }

    @Override
    public void editItemFromRestaurantMenu(Long itemId, String itemName, String description, double price, String menuImage) throws ItemNotFoundException {
        MenuItem menuItem = menuItemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        menuItem.itemName = itemName;
        menuItem.itemShortDescription = description;
        menuItem.price = price;
        menuItem.menuImage = menuImage;
        menuItemRepository.save(menuItem);
    }

    @Override
    public void addQuantityToMenuItem(Long itemId, int quantity) throws ItemNotFoundException {
        MenuItem menuItem = menuItemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        menuItem.quantity += quantity;
        menuItemRepository.save(menuItem);
    }

    @Override
    public Restaurant getRestaurant(Long restaurantId) throws UnknownRestaurantException {
        return restaurantRepository.findById(restaurantId).orElseThrow(UnknownRestaurantException::new);
    }

    public List<Restaurant> getAllRestaurants()
    {
        return restaurantRepository.findAll();
    }

    public List<MenuItem> getRestaurantMenu(Long restaurantId) throws UnknownRestaurantException{
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(UnknownRestaurantException::new);
        return menuItemRepository.findAllByRestaurant(restaurant);
    }


    @Override
    public Restaurant loginRestaurant(String email, String password) throws EmailNotFoundException, IncorrectPasswordException, AccountNotActiveException {
        Restaurant restaurant = restaurantRepository.findByRestaurantEmail(email).orElseThrow(EmailNotFoundException::new);
        if(!restaurant.isActive)
            throw new AccountNotActiveException();
        if(passwordEncoder.matches(password,restaurant.restaurantPassword))
            return restaurant;
        else
            throw new IncorrectPasswordException();
    }

}
