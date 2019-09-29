package com.finki.MyTable;

import com.finki.MyTable.model.*;
import com.finki.MyTable.model.factory.*;
import com.finki.MyTable.repository.OrderedMenuItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderedMenuItemRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OrderedMenuItemRepository orderedMenuItemRepository;

    Customer customer;
    Restaurant restaurant;
    MenuItem menuItem;
    CustomerOrder customerOrder;
    OrderedMenuItem orderedMenuItem;

    @Before
    public void setup() {
        this.customer = CustomerFactory.create("testCust", "LastNameTest", "test@mail.com", "pwd123");
        this.restaurant = RestaurantFactory.create("lalaRest", "Negde i nekade 23", "food@mail.com", "pwd222", "logo.jpg", 1234L);
        this.menuItem = MenuItemFactory.create("salata", "salata", 10, 10, this.restaurant, "image.jpg");

        this.customerOrder = CustomerOrderFactory.create(this.customer, this.restaurant, 12, LocalDateTime.now());
        this.orderedMenuItem = OrderedMenuItemFactory.create(this.customerOrder, this.menuItem, 10);

        testEntityManager.persist(this.customer);
        testEntityManager.persist(this.restaurant);
        testEntityManager.persist(this.menuItem);
        testEntityManager.persist(this.customerOrder);
        testEntityManager.persist(this.orderedMenuItem);
        testEntityManager.flush();
    }

    @Test
    public void whenFindAllByCustomerOrder_thenReturnOrderedMenuItems() {

        List<OrderedMenuItem> found = orderedMenuItemRepository.findAllByCustomerOrder(this.customerOrder);

        assertThat(found.get(0)).isEqualTo(this.orderedMenuItem);
    }

    @Test
    public void whenFindByCustomerAndMenuItem_thenReturnOrderedMenuItem() {
        Optional<OrderedMenuItem> found = orderedMenuItemRepository.findByCustomerOrderAndMenuItem(this.customerOrder, this.menuItem);

        found.ifPresent(item -> assertThat(this.orderedMenuItem).isEqualTo(item));
    }

    @Test
    public void whenFindAllByMenuItem_thenReturnOrderedMenuItems() {
        Optional<OrderedMenuItem> found = orderedMenuItemRepository.findAllByMenuItem(this.menuItem);

        found.ifPresent(item -> assertThat(this.orderedMenuItem).isEqualTo(item));
    }
}
