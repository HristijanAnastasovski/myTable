package com.finki.MyTable.model.view;

import com.finki.MyTable.model.CustomerOrder;
import com.finki.MyTable.model.OrderedMenuItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderView {
    public Long orderId;
    public String restaurantEmail;
    public String customerFirstName;
    public String customerLastName;
    public String customerEmail;
    public Long customerCreditCard;
    public int CVV;
    public int numberOfSeats;
    public LocalDateTime dateTime;
    public double total;

    public List<OrderedItemView> orderedItems;

    public OrderView(CustomerOrder customerOrder, Long customerCreditCard, int CVV, List<OrderedItemView> orderedItems)
    {
        orderId = customerOrder.id;
        restaurantEmail = customerOrder.restaurant.restaurantEmail;
        customerFirstName = customerOrder.customer.firstName;
        customerLastName = customerOrder.customer.lastName;
        customerEmail = customerOrder.customer.email;
        this.customerCreditCard = customerCreditCard;
        this.CVV = CVV;
        numberOfSeats = customerOrder.numberOfSeats;
        dateTime = customerOrder.dateTime;
        this.orderedItems = new ArrayList<>();
        this.orderedItems.addAll(orderedItems);
        for (OrderedItemView orderedItemView: orderedItems) {
            total+=orderedItemView.quantity*orderedItemView.price;
        }
    }
}
