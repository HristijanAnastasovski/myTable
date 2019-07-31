package com.finki.MyTable.model.factory;

import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.CustomerOrder;
import com.finki.MyTable.model.Restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class CustomerOrderFactory {
    public static CustomerOrder create(Customer customer, Restaurant restaurant, int numberOfSeats, LocalDateTime dateTime)
    {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.customer=customer;
        customerOrder.restaurant=restaurant;
        customerOrder.numberOfSeats=numberOfSeats;
        customerOrder.dateTime=dateTime;


        return customerOrder;
    }
}
