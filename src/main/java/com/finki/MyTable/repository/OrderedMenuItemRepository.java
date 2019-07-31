package com.finki.MyTable.repository;

import com.finki.MyTable.model.CustomerOrder;
import com.finki.MyTable.model.MenuItem;
import com.finki.MyTable.model.OrderedMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderedMenuItemRepository extends JpaRepository<OrderedMenuItem, Long> {


    List<OrderedMenuItem> findAllByCustomerOrder(CustomerOrder customerOrder);
    Optional<OrderedMenuItem> findByCustomerOrderAndMenuItem(CustomerOrder customerOrder, MenuItem menuItem);
    Optional<OrderedMenuItem> findAllByMenuItem(MenuItem menuItem);
}
