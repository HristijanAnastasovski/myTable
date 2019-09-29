package com.finki.MyTable;

import com.finki.MyTable.model.MenuItem;
import com.finki.MyTable.model.Restaurant;
import com.finki.MyTable.model.exception.*;
import com.finki.MyTable.model.factory.MenuItemFactory;
import com.finki.MyTable.model.factory.RestaurantFactory;
import com.finki.MyTable.repository.MenuItemRepository;
import com.finki.MyTable.repository.RestaurantRepository;
import com.finki.MyTable.service.RestaurantManagementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RestaurantManagementServiceImplTest {

    @Spy
    RestaurantManagementService restaurantManagementService;

    @MockBean
    RestaurantRepository restaurantRepository;

    @MockBean
    MenuItemRepository menuItemRepository;


    Restaurant restaurant;

    @Before
    public void init() {
        this.restaurant = RestaurantFactory.create("near", "krmwekr", "roewmor@mail.com", "okerwoker",
                "roewrmo.png", 12345L);
    }

    @Test
    public void whenRegisterRestaurant_thenReturnRestaurant() throws RestaurantAlreadyExistsException, EmailAlreadyInUseException {
        when(restaurantManagementService.registerNewRestaurant("near", "krmwekr", "roewmor@mail.com", "okerwoker",
                "roewrmo.png", 12345L)).thenReturn(this.restaurant);

        Restaurant restaurant = restaurantManagementService.registerNewRestaurant("near", "krmwekr", "roewmor@mail.com", "okerwoker",
                "roewrmo.png", 12345L);

        assertThat(restaurant).isNotNull();
        assertThat(restaurant.restaurantName).isEqualTo("near");
    }

    @Test
    public void whenChangeRestaurantInformation_thenReturnRestaurant() throws UnknownRestaurantException, RestaurantAlreadyExistsException {
        when(restaurantManagementService.changeRestaurantInformation(1L, "rwemr", "krmwekr", "ddwq", 12312L))
                .thenReturn(restaurant);

        Restaurant restaurant = restaurantManagementService.changeRestaurantInformation(1L, "rwemr", "krmwekr", "ddwq", 12312L);

        assertThat(restaurant.restaurantAddress).isEqualTo("krmwekr");
    }

    @Test
    public void whenChangeRestaurantPassword_thenReturnRestaurantPassword() throws UnknownRestaurantException, NoDifferenceInPasswordsException {
        when(restaurantManagementService.changeRestaurantPassword(1L, "newPassword")).thenReturn(this.restaurant);

        Restaurant result = restaurantManagementService.changeRestaurantPassword(1L, "newPassword");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        assertThat(passwordEncoder.matches(result.restaurantPassword, "okerwoker")).isFalse();
    }

    @Test
    public void whenRemoveRestaurant_thenDoNothing() throws UnknownRestaurantException {
        doNothing().when(restaurantManagementService).removeRestaurant(1L);
    }

    @Test
    public void whenAddItemToRestaurant_thenRestaurantHasOneMoreItem() throws ItemWithThatNameAlreadyExistsInMenuException, UnknownRestaurantException {
        doNothing().when(restaurantManagementService).addItemToRestaurantMenu("twt", "rwer",
                10, 10, 1L, "rwerwe");
    }

    @Test
    public void whenRemoveItemToRestaurant_thenRestaurantHasOneLessItem() throws ItemNotFoundException {
        doNothing().when(restaurantManagementService).removeItemFromRestaurantMenu(1L);
    }

    @Test
    public void whenEditItemFromRestaurant_thenRestaurantHasSameNumberOfItems() throws ItemNotFoundException {
        doNothing().when(restaurantManagementService).editItemFromRestaurantMenu(10L, "eoqw", "eoqwke", 312, "sdas");
    }

    @Test
    public void whenAddQuantityToMenuItem_thenMenuItemQuantityIsChanged() throws ItemNotFoundException {
        doNothing().when(restaurantManagementService).addQuantityToMenuItem(1L, 4);
    }

    @Test
    public void whenGetRestaurant_thenReturnRestaurant() throws UnknownRestaurantException {
        when(restaurantManagementService.getRestaurant(1L)).thenReturn(this.restaurant);

        Restaurant restaurant = restaurantManagementService.getRestaurant(1L);

        assertThat(restaurant).isNotNull();
    }

    @Test
    public void whenGetAllRestaurants_thenReturnAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(this.restaurant);

        when(restaurantManagementService.getAllRestaurants()).thenReturn(restaurants);

        List<Restaurant> result = restaurantManagementService.getAllRestaurants();

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void whenGetRestaurantMenuItems_thenReturnRestaurantMenuItems() throws UnknownRestaurantException {
        MenuItem item = MenuItemFactory.create("eiqowkoe", "eqowme", 231, 10, this.restaurant, "eqweq");
        List<MenuItem> items = new ArrayList<>();
        items.add(item);

        when(restaurantManagementService.getRestaurantMenu(1L)).thenReturn(items);

        List<MenuItem> result = restaurantManagementService.getRestaurantMenu(1L);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void whenLoginRestaurant_thenReturnRestaurant() throws EmailNotFoundException, IncorrectPasswordException, AccountNotActiveException {
        when(restaurantManagementService.loginRestaurant("pada@mail.com", "owkrowekro")).thenReturn(this.restaurant);

        Restaurant restaurant = restaurantManagementService.loginRestaurant("pada@mail.com", "owkrowekro");

        assertThat(restaurant).isNotNull();
    }
}
