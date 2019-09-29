package com.finki.MyTable;

import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.CustomerOrder;
import com.finki.MyTable.model.Restaurant;
import com.finki.MyTable.model.factory.CustomerFactory;
import com.finki.MyTable.model.factory.CustomerOrderFactory;
import com.finki.MyTable.model.factory.RestaurantFactory;
import com.finki.MyTable.repository.CustomerOrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerOrderRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Test
    public void whenFindAllByCustomer_thenReturnAllOrdersForCustomer() {
        Customer customer = CustomerFactory.create("testCust", "LastNameTest", "test@mail.com", "pwd123");
        Restaurant restaurant = RestaurantFactory.create("lalaRest", "Negde i nekade 23", "food@mail.com", "pwd222", "logo.jpg", 1234L);

        CustomerOrder customerOrder = CustomerOrderFactory.create(customer, restaurant, 12, LocalDateTime.now());
        CustomerOrder anotherCustomerOrder = CustomerOrderFactory.create(customer, restaurant, 10, LocalDateTime.now());

        testEntityManager.persist(customer);
        testEntityManager.persist(restaurant);
        testEntityManager.persist(customerOrder);
        testEntityManager.persist(anotherCustomerOrder);
        testEntityManager.flush();

        List<CustomerOrder> found = customerOrderRepository.findAllByCustomer(customer);

        assertThat(found.size()).isEqualTo(2);
    }

    @Test
    public void whenFindAllByRestaurant_thenReturnAllOrdersForRestaurant() {
        Customer customer = CustomerFactory.create("testCust", "LastNameTest", "test@mail.com", "pwd123");
        Restaurant restaurant = RestaurantFactory.create("lalaRest", "Negde i nekade 23", "food@mail.com", "pwd222", "logo.jpg", 1234L);

        CustomerOrder customerOrder = CustomerOrderFactory.create(customer, restaurant, 12, LocalDateTime.now());
        CustomerOrder anotherCustomerOrder = CustomerOrderFactory.create(customer, restaurant, 10, LocalDateTime.now());

        testEntityManager.persist(customer);
        testEntityManager.persist(restaurant);
        testEntityManager.persist(customerOrder);
        testEntityManager.persist(anotherCustomerOrder);
        testEntityManager.flush();

        List<CustomerOrder> found = customerOrderRepository.findAllByRestaurant(restaurant);

        assertThat(found.size()).isEqualTo(2);
    }
}
