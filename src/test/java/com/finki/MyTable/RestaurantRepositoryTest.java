package com.finki.MyTable;

import com.finki.MyTable.model.Restaurant;
import com.finki.MyTable.model.factory.RestaurantFactory;
import com.finki.MyTable.repository.CustomerOrderRepository;
import com.finki.MyTable.repository.RestaurantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RestaurantRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void whenFindByNameAndAddress_thenReturnRestaurant() {
        String name = "Kafana";
        String address = "Kapishtec bb";

        Restaurant restaurant = RestaurantFactory.create(name, address, "restaurant@mail.com", "pwd223", "logo.png", 213L);

        testEntityManager.persistAndFlush(restaurant);

        Optional<Restaurant> found = restaurantRepository.findByRestaurantNameAndRestaurantAddress(name, address);

        found.ifPresent(item -> assertThat(restaurant).isEqualTo(item));
    }

    @Test
    public void whenFindByEmail_thenReturnRestaurant() {
        String email = "restaurant@email.com";

        Restaurant restaurant = RestaurantFactory.create("name", "address", email, "pwd223", "logo.png", 213L);

        testEntityManager.persistAndFlush(restaurant);

        Optional<Restaurant> found = restaurantRepository.findByRestaurantEmail(email);

        found.ifPresent(item -> assertThat(restaurant).isEqualTo(item));
    }
}
