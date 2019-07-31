package com.finki.MyTable.repository;

import com.finki.MyTable.model.MenuItem;
import com.finki.MyTable.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findAllByRestaurant(Restaurant restaurant);
}
