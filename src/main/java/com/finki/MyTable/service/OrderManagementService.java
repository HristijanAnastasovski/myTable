package com.finki.MyTable.service;

import com.finki.MyTable.model.CustomerOrder;
import com.finki.MyTable.model.exception.*;
import com.finki.MyTable.model.view.OrderView;
import com.finki.MyTable.model.view.OrderedItemView;

import javax.mail.MessagingException;
import java.util.List;

public interface OrderManagementService {
    CustomerOrder createEmptyOrder(Long customerId, Long restaurantId, int numberOfSeats, int year, int month, int dayOfMonth, int hour, int minute) throws UnknownRestaurantException, UnknownCustomerException, LateOrderDateException, EarlyOrderDateException;
    CustomerOrder addItemToOrder(Long orderId, Long itemId, int quantity) throws OrderNotFoundException, ItemNotFoundException, QuantityException;
    CustomerOrder removeItemFromOrder(Long orderId, Long itemId) throws OrderNotFoundException, ItemNotFoundException, ItemNotInOrderException;
    OrderView confirmOrder(Long orderId, Long creditCardNumber, int CVV) throws OrderNotFoundException, MessagingException;
    List<CustomerOrder> getRestaurantOrders(Long restaurantId) throws UnknownRestaurantException;
    List<CustomerOrder> getCustomerOrders(Long customerId) throws UnknownCustomerException ;
    List<OrderedItemView> getOrderedItems(Long orderId) throws OrderNotFoundException;
    void removeOrder(Long orderId) throws OrderNotFoundException;
}
