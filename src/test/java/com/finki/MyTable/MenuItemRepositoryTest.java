package com.finki.MyTable;

import com.finki.MyTable.model.MenuItem;
import com.finki.MyTable.model.Restaurant;
import com.finki.MyTable.model.factory.MenuItemFactory;
import com.finki.MyTable.model.factory.RestaurantFactory;
import com.finki.MyTable.repository.MenuItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MenuItemRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    public void whenFindByRestaurant_returnMenuItems() {
        Restaurant restaurant = RestaurantFactory.create("lalaRest", "Negde i nekade 23", "food@mail.com",
                "pwd222", "logo.jpg", 1234L);

        MenuItem menuItem = MenuItemFactory.create("salata", "salata", 10, 10, restaurant, "image.jpg");

        testEntityManager.persist(restaurant);
        testEntityManager.persist(menuItem);
        testEntityManager.flush();

        List<MenuItem> found = menuItemRepository.findAllByRestaurant(restaurant);

        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0)).isEqualTo(menuItem);
    }
}
