package com.finki.MyTable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.factory.CustomerFactory;
import com.finki.MyTable.service.CustomerManagementService;
import com.finki.MyTable.web.CustomerManagementController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerManagementController.class)
public class CustomerManagementControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CustomerManagementService customerManagementService;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void whenGetCustomer_thenReturnCustomer() throws Exception {

        Customer customer = CustomerFactory.create("domas", "odmsad", "dosadk@a.com", "dowamd");
        given(customerManagementService.getCustomer(1L)).willReturn(customer);

        mvc.perform(get("/customer/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetCustomer_thenReturnCustomerCorrectInfo() throws Exception {
        Customer customer = CustomerFactory.create("domas", "odmsad", "dosadk@a.com", "dowamd");
        given(customerManagementService.getCustomer(1L)).willReturn(customer);

        mvc.perform(get("/customer/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("domas"));
    }

    @Test
    public void whenLoginCustomer_returnStatusOk() throws Exception {

        Customer customer = customerManagementService.loginCustomer("rewr", "rewrew");
        mvc.perform(post("/customer/login")
                .param("email", "mail@g.com")
                .param("password", "pwd1232q"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenLoginCustomerMissingCredentials_returnStatus400() throws Exception {
        Customer customer = customerManagementService.loginCustomer("rewr", "rewrew");
        mvc.perform(post("/customer/login")
                .param("password", "pwd1232q"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenRegisterCustomer_returnStatus200() throws Exception {
        mvc.perform(post("/customer/register")
                .param("firstName", "mawdw")
                .param("lastName", "domawo")
                .param("email", "dwadwa@mail.com")
                .param("password", "d2qodo020"))
                .andExpect(status().isOk());

    }

    @Test
    public void whenEditCustomer_returnStatus200() throws Exception {
        mvc.perform((post("/customer/edit"))
                .param("id", "1")
                .param("firstName", "eqe")
                .param("lastName", "dasdok"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenChangePassword_returnStatus200() throws Exception {
        mvc.perform(post("/customer/changePassword")
                .param("id", "1")
                .param("newPassword", "eloqwe"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCustomerRemove_return200() throws Exception {
        mvc.perform(post("/customer/remove")
                .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenEditCustomerMissingCredentials_returnStatus400() throws Exception {
        mvc.perform((post("/customer/edit"))
                .param("id", "2"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCustomerChangePasswordWithoutNew_returnStatus400() throws Exception {
        mvc.perform((post("/customer/changePassword"))
                .param("id", "1"))
                .andExpect(status().is4xxClientError());
    }
}
