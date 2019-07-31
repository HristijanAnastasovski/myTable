package com.finki.MyTable.web;

import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.MenuItem;
import com.finki.MyTable.model.Restaurant;
import com.finki.MyTable.model.exception.*;
import com.finki.MyTable.service.RestaurantManagementService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantManagementController {
    private final RestaurantManagementService restaurantManagementService;

    public RestaurantManagementController(RestaurantManagementService restaurantManagementService) {
        this.restaurantManagementService = restaurantManagementService;
    }

    @RequestMapping(value = "/restaurant/{restaurantId}", method = RequestMethod.GET)
    public Restaurant getRestaurant(@PathVariable("restaurantId") Long restaurantId) throws UnknownRestaurantException {
        return restaurantManagementService.getRestaurant(restaurantId);
    }

    @RequestMapping(value="/restaurant/login", method = RequestMethod.POST)
    public Restaurant loginCustomer(@RequestParam("email") String email, @RequestParam("password") String password) throws EmailNotFoundException, IncorrectPasswordException, AccountNotActiveException {
        return restaurantManagementService.loginRestaurant(email, password);
    }

    @RequestMapping(value = "/restaurant/allRestaurants", method = RequestMethod.GET)
    public List<Restaurant> getRestaurant() {
        return restaurantManagementService.getAllRestaurants();
    }

    @RequestMapping(value = "/restaurant/menu/{restaurantId}", method = RequestMethod.GET)
    public List<MenuItem> getRestaurantMenu(@PathVariable("restaurantId") Long restaurantId) throws UnknownRestaurantException {
        return restaurantManagementService.getRestaurantMenu(restaurantId);
    }

    @RequestMapping(value = "/restaurant/register", method = RequestMethod.POST)
    public Restaurant registerRestaurant(@RequestParam("restaurantName") String restaurantName,
                                         @RequestParam("restaurantAddress") String restaurantAddress,
                                         @RequestParam("restaurantEmail") String restaurantEmail,
                                         @RequestParam("restaurantPassword") String restaurantPassword,
                                         @RequestParam("restaurantLogo") String restaurantLogo,
                                         @RequestParam("billingAccount") Long billingAccount) throws RestaurantAlreadyExistsException, EmailAlreadyInUseException {
        return restaurantManagementService.registerNewRestaurant(restaurantName,restaurantAddress,restaurantEmail,restaurantPassword,restaurantLogo,billingAccount);
    }

    @RequestMapping(value = "/restaurant/edit", method = RequestMethod.POST)
    public Restaurant editRestaurant(@RequestParam("restaurantId") Long restaurantId,
                                         @RequestParam("restaurantName") String restaurantName,
                                         @RequestParam("restaurantAddress") String restaurantAddress,
                                         @RequestParam("restaurantLogo") String restaurantLogo,
                                         @RequestParam("billingAccount") Long billingAccount) throws UnknownRestaurantException, RestaurantAlreadyExistsException {
        return restaurantManagementService.changeRestaurantInformation(restaurantId,restaurantName,restaurantAddress,restaurantLogo, billingAccount);
    }

    @RequestMapping(value = "/restaurant/changePassword", method = RequestMethod.POST)
    public Restaurant changePasswordToRestaurant(@RequestParam("restaurantId") Long restaurantId,
                                         @RequestParam("newPassword") String newPassword) throws UnknownRestaurantException, NoDifferenceInPasswordsException {
        return restaurantManagementService.changeRestaurantPassword(restaurantId,newPassword);
    }

    @RequestMapping(value = "/restaurant/remove", method = RequestMethod.POST)
    public void removeRestaurant(@RequestParam("id") Long id) throws UnknownRestaurantException {
        restaurantManagementService.removeRestaurant(id);
    }

    @RequestMapping(value="/restaurant/menu/addItem", method= RequestMethod.POST)
    public void addMenuItem(@RequestParam("itemName") String itemName,
                                        @RequestParam("description") String description,
                                        @RequestParam("price") double price,
                                        @RequestParam("quantity") int quantity,
                                        @RequestParam("restaurantId") Long restaurantId,
                                        @RequestParam("menuItemImage") String menuItemImage) throws ItemWithThatNameAlreadyExistsInMenuException, UnknownRestaurantException {
        restaurantManagementService.addItemToRestaurantMenu(itemName,description,price,quantity,restaurantId,menuItemImage);
    }

    @RequestMapping(value="/restaurant/menu/addQuantity", method = RequestMethod.POST)
    public void addQuantityToItem(@RequestParam("itemId") Long itemId, @RequestParam("quantity") int quantity) throws ItemNotFoundException {
        restaurantManagementService.addQuantityToMenuItem(itemId,quantity);
    }

    @RequestMapping(value="/restaurant/menu/editItem", method=RequestMethod.POST)
    public void editMenuItem(@RequestParam("itemId") Long itemId,
                             @RequestParam("itemName") String itemName,
                             @RequestParam("description") String description,
                             @RequestParam("price") double price,
                             @RequestParam("menuItemImage") String menuItemImage
                             ) throws ItemNotFoundException {
        restaurantManagementService.editItemFromRestaurantMenu(itemId, itemName, description, price,menuItemImage);
    }

    @RequestMapping(value = "restaurant/menu/deleteItem", method=RequestMethod.POST)
    public void removeMenuItem(@RequestParam("id") Long id) throws ItemNotFoundException {
        restaurantManagementService.removeItemFromRestaurantMenu(id);
    }







}
