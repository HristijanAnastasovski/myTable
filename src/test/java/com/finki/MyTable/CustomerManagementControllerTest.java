package com.finki.MyTable;

import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.factory.CustomerFactory;
import com.finki.MyTable.service.CustomerManagementService;
import com.finki.MyTable.web.CustomerManagementController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerManagementController.class)
public class CustomerManagementControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CustomerManagementService customerManagementService;

    @Test
    public void whenGetCustomer_thenReturnCustomer() throws Exception {

        Customer customer = CustomerFactory.create("domas", "odmsad", "dosadk@a.com", "dowamd");
        given(customerManagementService.getCustomer(1L)).willReturn(customer);

        mvc.perform(get("/customer/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenLoginCustomer_returnStatusOk() throws Exception {

        Customer customer = customerManagementService.loginCustomer("rewr", "rewrew");
        mvc.perform(post("/customer/login")
                .param("email", "mail@g.com")
                .param("password", "pwd1232q"))
                .andExpect(status().isOk());
    }
}
