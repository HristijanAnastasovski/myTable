package com.finki.MyTable;

import com.finki.MyTable.model.*;
import com.finki.MyTable.model.exception.*;
import com.finki.MyTable.model.factory.*;
import com.finki.MyTable.model.view.OrderView;
import com.finki.MyTable.model.view.OrderedItemView;
import com.finki.MyTable.repository.*;
import com.finki.MyTable.repository.mail.MailSenderRepository;
import com.finki.MyTable.service.OrderManagementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderManagementServiceImplTest {

    @Spy
    OrderManagementService orderManagementService;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    CustomerOrderRepository customerOrderRepository;

    @MockBean
    MenuItemRepository menuItemRepository;

    @MockBean
    RestaurantRepository restaurantRepository;

    @MockBean
    OrderedMenuItemRepository orderedMenuItemRepository;

    @MockBean
    MailSenderRepository mailSenderRepository;

    Restaurant restaurant;
    Customer customer;
    CustomerOrder customerOrder;

    @Before
    public void init() {
        this.restaurant = RestaurantFactory.create("name", "rewr", "rewr@mali.com",
                "rwerw", "wer.png", 1234L);
        this.customer = CustomerFactory.create("cust", "lastname", "mail@m.com", "psalfpk3");
        this.customerOrder = CustomerOrderFactory.create(customer, restaurant, 10, LocalDateTime.now());
    }

    @Test
    public void whenCreateEmptyOrder_thenReturnEmptyOrder() throws UnknownRestaurantException, LateOrderDateException, UnknownCustomerException, EarlyOrderDateException {
        when(orderManagementService.createEmptyOrder(1L, 1L, 10, 10, 10, 10, 10, 10))
                .thenReturn(this.customerOrder);

        CustomerOrder emptyOrder = orderManagementService.createEmptyOrder(1L, 1L, 10,
                10, 10, 10, 10, 10);

        assertThat(emptyOrder).isNotNull();
        assertThat(emptyOrder.numberOfSeats).isEqualTo(10);
    }

    @Test
    public void whenAddItemToOrder_thenReturnOrderWithItemAdded() throws QuantityException, OrderNotFoundException, ItemNotFoundException {
        when(orderManagementService.addItemToOrder(1L, 2L, 10)).thenReturn(this.customerOrder);

        CustomerOrder order = orderManagementService.addItemToOrder(1L, 2L, 10);

        assertThat(order).isNotNull();
    }

    @Test(expected = QuantityException.class)
    public void whenAddItemToOrderInvalidQuantity_thenThrowQuantityException() throws QuantityException, OrderNotFoundException, ItemNotFoundException {
        when(orderManagementService.addItemToOrder(1L, 2L, 96796796)).thenThrow(QuantityException.class);

        orderManagementService.addItemToOrder(1L, 2L, 96796796);
    }

    @Test
    public void whenRemoveItemFromOrder_thenReturnOrderWithItemRemoved() throws ItemNotInOrderException, OrderNotFoundException, ItemNotFoundException {
        when(orderManagementService.removeItemFromOrder(1L, 1L)).thenReturn(this.customerOrder);

        CustomerOrder order = orderManagementService.removeItemFromOrder(1L, 1L);

        assertThat(order.orderedMenuItems.size()).isEqualTo(0);
    }

    @Test(expected = OrderNotFoundException.class)
    public void whenRemoveItemFromOrderNotExists_thenThrowOrderNotFoundException() throws ItemNotInOrderException, OrderNotFoundException, ItemNotFoundException {
        when(orderManagementService.removeItemFromOrder(1L, 1L)).thenThrow(OrderNotFoundException.class);

        orderManagementService.removeItemFromOrder(1L, 1L);
    }

    @Test
    public void whenConfirmOrder_thenReturnOrderView() throws OrderNotFoundException, MessagingException {
        OrderView orderView = new OrderView(this.customerOrder, 321312L, 124, new ArrayList<>());
        when(orderManagementService.confirmOrder(1L, 32423424234L, 124)).thenReturn(orderView);

        OrderView returnedOrderView = orderManagementService.confirmOrder(1L, 32423424234L, 124);

        assertThat(returnedOrderView.customerCreditCard).isEqualTo(321312L);
    }

    @Test
    public void whenGetRestaurantOrders_thenReturnOrders() throws UnknownRestaurantException {
        List<CustomerOrder> orders = new ArrayList<>();
        orders.add(this.customerOrder);

        when(orderManagementService.getRestaurantOrders(1L)).thenReturn(orders);

        List<CustomerOrder> returnedOrders = orderManagementService.getRestaurantOrders(1L);

        assertThat(returnedOrders.size()).isEqualTo(1);
    }

    @Test
    public void whenGetCustomerOrders_thenReturnCustomerOrders() throws UnknownCustomerException {
        List<CustomerOrder> orders = new ArrayList<>();
        orders.add(this.customerOrder);

        when(orderManagementService.getCustomerOrders(1L)).thenReturn(orders);

        List<CustomerOrder> returnedOrders = orderManagementService.getCustomerOrders(1L);

        assertThat(returnedOrders.size()).isEqualTo(1);
    }

    @Test
    public void whenGetOrderedItems_thenReturnOrderedItems() throws OrderNotFoundException {
        MenuItem menuItem = MenuItemFactory.create("eqww", "eqwe", 13, 432, this.restaurant, "rewr");
        OrderedMenuItem orderedMenuItem = OrderedMenuItemFactory.create(this.customerOrder, menuItem, 10);
        OrderedItemView orderedItemView = new OrderedItemView(orderedMenuItem);

        List<OrderedItemView> orderedItems = new ArrayList<>();
        orderedItems.add(orderedItemView);

        when(orderManagementService.getOrderedItems(1L)).thenReturn(orderedItems);

        List<OrderedItemView> result = orderManagementService.getOrderedItems(1L);

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void whenRemoveOrder_thenDoNothing() throws ItemNotInOrderException, OrderNotFoundException, ItemNotFoundException {
        doNothing().when(orderManagementService).removeOrder(1L);
    }
}
