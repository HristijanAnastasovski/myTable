package com.finki.MyTable;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.finki.MyTable.service.OrderManagementService;
import com.finki.MyTable.web.OrderManagementController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderManagementController.class)
public class OrderManagementControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    OrderManagementService orderManagementService;

    ObjectMapper om = new ObjectMapper();

    @Test
    public void whenCreateEmptyOrderValidInfo_thenReturnStatus200() throws Exception {
        mvc.perform((post("/order/createEmpty"))
                .param("customerId", "1")
                .param("restaurantId", "1")
                .param("numberOfSeats", "10")
                .param("year", "2020")
                .param("month", "9")
                .param("dayOfMonth", "2")
                .param("hour", "12")
                .param("minute", "59"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddItem_thenReturnStatus200() throws Exception {
        mvc.perform((post("/order/addItem"))
                .param("orderId", "1")
                .param("itemId", "1")
                .param("quantity", "120"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddItemMissingParams_thenReturnStatus400() throws Exception {
        mvc.perform(post("/order/addItem")
                .param("itemId", "1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCreateEmptyOrderMissingInfo_thenReturnStatus400() throws Exception {
        mvc.perform(post("/order/createEmpty"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenRemoveItem_thenReturnStatus200() throws Exception {
        mvc.perform((post("/order/removeItem"))
                .param("orderId", "1")
                .param("itemId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenConfirmOrder_thenReturnStatus200() throws Exception {
        mvc.perform(post("/order/confirm")
                .param("orderId", "1")
                .param("creditCardNumber", "213129321490")
                .param("CVV", "123"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetCustomerOrdersByRestaurant_thenReturnStatus200() throws Exception {
        mvc.perform((get("/order/allRestaurantOrders/1")))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetCustomerOrdersByCustomer_thenReturnStatus200() throws Exception {
        mvc.perform(get("/order/allCustomerOrders/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetOrderedItems_thenReturnStatus200() throws Exception {
        mvc.perform(get("/order/getOrderedItems/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenRemoveOrder_thenReturnStatus200() throws Exception {
        mvc.perform(post("/order/removeOrder")
                .param("orderId", "1"))
                .andExpect(status().isOk());
    }
}
