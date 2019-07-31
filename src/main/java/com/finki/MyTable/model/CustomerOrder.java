package com.finki.MyTable.model;

import com.finki.MyTable.model.factory.OrderedMenuItemFactory;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer_order")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_order_id")
    public Long id;

    @ManyToOne
    public Customer customer;

    @ManyToOne
    public Restaurant restaurant;

    public int numberOfSeats;

    public LocalDateTime dateTime;


    public boolean orderQuantityValidation(List<OrderedMenuItem> orderedItems){
        for (OrderedMenuItem orderedMenuItem : orderedItems) {
            if(orderedMenuItem.quantity> orderedMenuItem.menuItem.quantity)
                return false;
        }
        return true;
    }




}
