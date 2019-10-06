package com.finki.MyTable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finki.MyTable.service.RestaurantManagementService;
import com.finki.MyTable.web.RestaurantManagementController;
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
@WebMvcTest(RestaurantManagementController.class)
public class RestaurantManagementControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    RestaurantManagementService restaurantManagementService;

    ObjectMapper om = new ObjectMapper();


    @Test
    public void whenGetRestaurant_thenReturnStatus200() throws Exception {
        mvc.perform(get("/restaurant/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenLoginCustomer_thenReturnStatus200() throws Exception {
        mvc.perform(post("/restaurant/login")
                .param("email", "iwemie")
                .param("password", "widmwiei"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetRestaurants_thenReturnStatus200() throws Exception {
        mvc.perform(get("/restaurant/allRestaurants"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenEditMenu_thenReturnStatus200() throws Exception {
        mvc.perform(get("/restaurant/menu/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenRegisterRestaurant_thenReturnStatus200() throws Exception {
        mvc.perform(post("/restaurant/register")
                .param("restaurantName", "owemioame")
                .param("restaurantAddress", "welwmke")
                .param("restaurantEmail", "doawmomw")
                .param("restaurantPassword", "ewqolmeoqwme")
                .param("restaurantLogo", "dmwmdi")
                .param("billingAccount", "123213"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenChangePasswordToRestaurant_thenReturnStatus200() throws Exception {
        mvc.perform(post("/restaurant/changePassword")
                .param("restaurantId", "1")
                .param("newPassword", "Ddwqwdqwd"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenRemoveRestaurant_thenReturnStatus200() throws Exception {
        mvc.perform(post("/restaurant/remove")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddMenuItem_thenReturnStatus200() throws Exception {
        mvc.perform(post("/restaurant/menu/addItem")
                .param("description", "eqweqwe")
                .param("itemName", "ewqeqw")
                .param("price", "123")
                .param("quantity", "123")
                .param("restaurantId", "1")
                .param("menuItemImage", "eqweqw"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenAddMenuItemAndRestaurantIdNotNumber_thenReturnStatus400() throws Exception {
        mvc.perform(post("/restaurant/menu/addItem")
                .param("description", "eqweqwe")
                .param("itemName", "ewqeqw")
                .param("price", "123")
                .param("quantity", "123")
                .param("restaurantId", "WRONG TYPE")
                .param("menuItemImage", "eqweqw"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenAddMenuItemAndPriceNotNumber_thenReturnStatus400() throws Exception {
        mvc.perform(post("/restaurant/menu/addItem")
                .param("description", "eqweqwe")
                .param("itemName", "ewqeqw")
                .param("price", "WRONG TYPE")
                .param("quantity", "123")
                .param("restaurantId", "1")
                .param("menuItemImage", "eqweqw"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenAddMenuItemAndQuantityNotNumber_thenReturnStatus400() throws Exception {
        mvc.perform(post("/restaurant/menu/addItem")
                .param("description", "eqweqwe")
                .param("itemName", "ewqeqw")
                .param("price", "123")
                .param("quantity", "WRONG TYPE")
                .param("restaurantId", "1")
                .param("menuItemImage", "eqweqw"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void whenAddQuantityToItem_thenReturnStatus200() throws Exception {
        mvc.perform(post("/restaurant/menu/addQuantity")
                .param("itemId", "1")
                .param("quantity", "23"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenEditMenuItem_thenReturnStatus200() throws Exception {
        mvc.perform(post("/restaurant/menu/editItem")
                .param("itemId", "1")
                .param("itemName", "wtwet")
                .param("description", "eqweq")
                .param("price", "123124")
                .param("menuItemImage", "eiowqjei")
                .param("quantity", "23"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenRemoveMenuItem_thenReturnStatus200() throws Exception {
        mvc.perform(post("/restaurant/menu/deleteItem")
                .param("id", "1"))
                .andExpect(status().isOk());
    }
}
