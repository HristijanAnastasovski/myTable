package com.finki.MyTable;

import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.exception.CustomerWithSameEmailExistsException;
import com.finki.MyTable.model.exception.NoDifferenceInPasswordsException;
import com.finki.MyTable.model.exception.UnknownCustomerException;
import com.finki.MyTable.model.factory.CustomerFactory;
import com.finki.MyTable.repository.CustomerOrderRepository;
import com.finki.MyTable.repository.CustomerRepository;
import com.finki.MyTable.service.CustomerManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CustomerManagementImplTest {


    @Spy
    CustomerManagementService customerManagementService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerOrderRepository customerOrderRepository;

    @Test
    public void whenRegisterNewCustomer_thenReturnNewCustomer() throws CustomerWithSameEmailExistsException {
        Customer customer = CustomerFactory.create("firstname", "lastname", "mail@cc.com", "changed");
        when(customerManagementService.registerNewCustomer("firstname", "lastname", "mail@cc.com", "changed")).thenReturn(customer);

        Customer registered = customerManagementService.registerNewCustomer("firstname", "lastname", "mail@cc.com", "changed");

        assertThat(registered.email).isEqualTo("mail@cc.com");
    }

    @Test
    public void whenChangeDetailsToCustomer_thenReturnChangedCustomer() throws UnknownCustomerException {
        Customer customer = CustomerFactory.create("firstname", "changed", "mail@cc.com", "changed");
        when(customerManagementService.changeDetailsToCustomer(1L, "changedName", "changed")).thenReturn(customer);
        customerManagementService.changeDetailsToCustomer(1L, "changedName", "changed");

        Customer changed = customerManagementService.changeDetailsToCustomer(1L, "changedName", "changed");

        assertThat(changed.lastName).isEqualTo("changed");
    }

    @Test
    public void whenChangePassword_thenReturnChangedCustomer() throws UnknownCustomerException, NoDifferenceInPasswordsException {
        Customer customer = CustomerFactory.create("firstname", "changed", "mail@cc.com", "changed");
        when(customerManagementService.changePassword(1L, "changed")).thenReturn(customer);

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        assertThat(encoder.matches("changed", customer.password)).isTrue();
    }

    @Test
    public void whenGetCustomer_thenReturnCustomer() throws UnknownCustomerException {
        Customer customer = CustomerFactory.create("firstname", "changed", "mail@cc.com", "changed");
        when(customerManagementService.getCustomer(1L)).thenReturn(customer);

        Customer found = customerManagementService.getCustomer(1L);

        assertThat(found.firstName).isEqualTo("firstname");
    }

    @Test
    public void whenRemoveCustomer_thenDoNothing() throws UnknownCustomerException {
        doNothing().when(customerManagementService).removeCustomer(1L);
    }
}
