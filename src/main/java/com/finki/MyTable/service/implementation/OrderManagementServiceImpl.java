package com.finki.MyTable.service.implementation;

import com.finki.MyTable.model.*;
import com.finki.MyTable.model.MenuItem;
import com.finki.MyTable.model.exception.*;
import com.finki.MyTable.model.factory.CustomerOrderFactory;
import com.finki.MyTable.model.factory.OrderedMenuItemFactory;
import com.finki.MyTable.model.view.OrderView;
import com.finki.MyTable.model.view.OrderedItemView;
import com.finki.MyTable.repository.*;
import com.finki.MyTable.repository.mail.MailSenderRepository;
import com.finki.MyTable.service.OrderManagementService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderManagementServiceImpl implements OrderManagementService {
    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderedMenuItemRepository orderedMenuItemRepository;
    private final MailSenderRepository mailSenderRepository;
    private final KafkaTemplate<String,OrderView> kafkaTemplate;

    public OrderManagementServiceImpl(CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository, MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository, OrderedMenuItemRepository orderedMenuItemRepository, MailSenderRepository mailSenderRepository, KafkaTemplate<String, OrderView> kafkaTemplate) {
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderedMenuItemRepository = orderedMenuItemRepository;
        this.mailSenderRepository = mailSenderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public CustomerOrder createEmptyOrder(Long customerId, Long restaurantId, int numberOfSeats, int year, int month, int dayOfMonth, int hour, int minute) throws UnknownRestaurantException, UnknownCustomerException, LateOrderDateException, EarlyOrderDateException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(UnknownCustomerException::new);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(UnknownRestaurantException::new);
        LocalDateTime dateTime=LocalDateTime.of(year,month,dayOfMonth,hour,minute);
        if(dateTime.isBefore(LocalDateTime.now().plusHours(2)))
            throw new LateOrderDateException();
        if(dateTime.isAfter(LocalDateTime.now().plusMonths(1)))
            throw new EarlyOrderDateException();
        CustomerOrder customerOrder = CustomerOrderFactory.create(customer,restaurant,numberOfSeats,dateTime);
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public CustomerOrder addItemToOrder(Long orderId, Long itemId, int quantity) throws OrderNotFoundException, ItemNotFoundException, QuantityException {
        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        MenuItem menuItem = menuItemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        List<OrderedMenuItem> orderedMenuItems = orderedMenuItemRepository.findAllByCustomerOrder(customerOrder);
        OrderedMenuItem orderedMenuItem = null;



        for(OrderedMenuItem omi : orderedMenuItems)
        {
            if(omi.menuItem.id.equals(itemId))
            {
                orderedMenuItem = omi;
                break;
            }
        }

        if(orderedMenuItem == null){
            if(menuItem.quantity>= quantity)
            orderedMenuItem =  OrderedMenuItemFactory.create(customerOrder, menuItem, quantity);
            else
                throw new QuantityException(menuItem.itemName);
        }else{
            if(menuItem.quantity >= orderedMenuItem.quantity+quantity)
            orderedMenuItem.quantity+=quantity;
            else
                throw new QuantityException(menuItem.itemName);
        }

        orderedMenuItemRepository.save(orderedMenuItem);

        return customerOrder;
    }

    @Override
    public CustomerOrder removeItemFromOrder(Long orderId, Long itemId) throws OrderNotFoundException, ItemNotFoundException, ItemNotInOrderException {
        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        MenuItem menuItem = menuItemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        OrderedMenuItem orderedMenuItem = orderedMenuItemRepository.findByCustomerOrderAndMenuItem(customerOrder,menuItem).orElseThrow(ItemNotInOrderException::new);
        orderedMenuItemRepository.delete(orderedMenuItem);

        return customerOrder;

    }

    @Override
    public OrderView confirmOrder(Long orderId, Long creditCardNumber, int CVV) throws OrderNotFoundException, MessagingException {
        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        List<OrderedMenuItem> orderedItems = orderedMenuItemRepository.findAllByCustomerOrder(customerOrder);
        List<OrderedItemView> orderedItemsView = new ArrayList<>();
        if(customerOrder.orderQuantityValidation(orderedItems)){
            //quantity reduction
            for (OrderedMenuItem orderedMenuItem : orderedItems) {
                OrderedItemView orderedItemView = new OrderedItemView(orderedMenuItem);
                orderedItemsView.add(orderedItemView);

                 MenuItem item = orderedMenuItem.menuItem;
                 item.quantity -= orderedMenuItem.quantity;
                 menuItemRepository.save(item);
            }
        }
        //check credit card

        OrderView orderView = new OrderView(customerOrder, creditCardNumber, CVV, orderedItemsView);

        String subject = "New order [#"+orderView.orderId+"]";
        String body = "<p>A new order [#"+orderView.orderId+"] was created for your restaurant!</p>" +
                "<p>Order created by: "+orderView.customerFirstName+" "+orderView.customerLastName+"</p>"+
                "<p>For full information visit <a href=\"http://localhost:3000\">our web page</a> and check your orders.</p>" +
                "<br><p>My Table</p>";

        mailSenderRepository.sendMail(orderView.restaurantEmail,subject,body);


        return orderView;
    }

    @Override
    public List<CustomerOrder> getRestaurantOrders(Long restaurantId) throws UnknownRestaurantException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(UnknownRestaurantException::new);
        return customerOrderRepository.findAllByRestaurant(restaurant);
    }

    @Override
    public List<CustomerOrder> getCustomerOrders(Long customerId) throws UnknownCustomerException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(UnknownCustomerException::new);
        return customerOrderRepository.findAllByCustomer(customer);

    }

    @Override
    public List<OrderedItemView> getOrderedItems(Long orderId) throws OrderNotFoundException {
        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        List<OrderedMenuItem> orderedItems = orderedMenuItemRepository.findAllByCustomerOrder(customerOrder);

        List<OrderedItemView> orderedItemsView = new ArrayList<>();

            for (OrderedMenuItem orderedMenuItem : orderedItems) {
                OrderedItemView orderedItemView = new OrderedItemView(orderedMenuItem);
                orderedItemsView.add(orderedItemView);
            }


        return orderedItemsView;
    }

    @Override
    public void removeOrder(Long orderId) throws OrderNotFoundException {
        CustomerOrder customerOrder = customerOrderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        List<OrderedMenuItem> orderedItems = new ArrayList<>(orderedMenuItemRepository.findAllByCustomerOrder(customerOrder));
        for(OrderedMenuItem oi : orderedItems){
            orderedMenuItemRepository.delete(oi);
        }
        customerOrderRepository.delete(customerOrder);
    }


}
