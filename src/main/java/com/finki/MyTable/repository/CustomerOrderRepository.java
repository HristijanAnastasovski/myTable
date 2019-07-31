package com.finki.MyTable.repository;

import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.CustomerOrder;
import com.finki.MyTable.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CustomerOrderRepository extends JpaRepository<CustomerOrder,Long> {
    List<CustomerOrder> findAllByRestaurant(Restaurant restaurant);
    List<CustomerOrder> findAllByCustomer(Customer restaurant);
}
