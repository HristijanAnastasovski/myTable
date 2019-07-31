package com.finki.MyTable.web;

import com.finki.MyTable.model.CustomerOrder;
import com.finki.MyTable.model.exception.*;
import com.finki.MyTable.model.view.OrderView;
import com.finki.MyTable.model.view.OrderedItemView;
import com.finki.MyTable.service.OrderManagementService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderManagementController {
    private final OrderManagementService orderManagementService;

    public OrderManagementController(OrderManagementService orderManagementService) {
        this.orderManagementService = orderManagementService;
    }

    @RequestMapping(value = "/order/createEmpty", method = RequestMethod.POST)
    public CustomerOrder createEmptyOrder(@RequestParam("customerId") Long customerId,
                                          @RequestParam("restaurantId") Long restaurantId,
                                          @RequestParam("numberOfSeats") int numberOfSeats,
                                          @RequestParam("year") int year,
                                          @RequestParam("month") int month,
                                          @RequestParam("dayOfMonth") int dayOfMonth,
                                          @RequestParam("hour") int hour,
                                          @RequestParam("minute") int minute
                                          ) throws UnknownRestaurantException, LateOrderDateException, UnknownCustomerException, EarlyOrderDateException {
         return orderManagementService.createEmptyOrder(customerId, restaurantId, numberOfSeats, year, month, dayOfMonth, hour, minute);
    }

    @RequestMapping(value = "/order/addItem", method = RequestMethod.POST)
    public CustomerOrder addItemToOrder(@RequestParam("orderId") Long orderId,
                                        @RequestParam("itemId") Long itemId,
                                        @RequestParam("quantity") int quantity) throws OrderNotFoundException, QuantityException, ItemNotFoundException {
        return orderManagementService.addItemToOrder(orderId, itemId, quantity);
    }

    @RequestMapping(value = "/order/removeItem", method = RequestMethod.POST)
    public CustomerOrder removeItemFromOrder(@RequestParam("orderId") Long orderId,
                                             @RequestParam("itemId") Long itemId) throws ItemNotInOrderException, OrderNotFoundException, ItemNotFoundException {
        return orderManagementService.removeItemFromOrder(orderId,itemId);
    }

    @RequestMapping(value = "/order/confirm", method = RequestMethod.POST)
    public OrderView confirmOrder(@RequestParam("orderId") Long orderId,
                                  @RequestParam("creditCardNumber") Long creditCardNumber,
                                  @RequestParam("CVV") int CVV) throws OrderNotFoundException, MessagingException {
        return orderManagementService.confirmOrder(orderId, creditCardNumber, CVV);
    }

    @RequestMapping(value = "/order/allRestaurantOrders/{restaurantId}", method = RequestMethod.GET)
    public List<CustomerOrder> allOrdersForRestaurant(@PathVariable("restaurantId") Long restaurantId) throws UnknownRestaurantException {
        return orderManagementService.getRestaurantOrders(restaurantId);
    }

    @RequestMapping(value = "/order/allCustomerOrders/{customerId}", method = RequestMethod.GET)
    public List<CustomerOrder> allOrdersFromCustomer(@PathVariable("customerId") Long customerId) throws UnknownCustomerException {
        return orderManagementService.getCustomerOrders(customerId);
    }

    @RequestMapping(value = "/order/getOrderedItems/{orderId}", method=RequestMethod.GET)
    public List<OrderedItemView> getOrderedItems(@PathVariable("orderId") Long orderId) throws OrderNotFoundException {
        return orderManagementService.getOrderedItems(orderId);
    }

    @RequestMapping(value = "/order/removeOrder", method = RequestMethod.POST)
    public void removeOrder(@RequestParam("orderId") Long orderId) throws OrderNotFoundException{
        orderManagementService.removeOrder(orderId);
    }
}
