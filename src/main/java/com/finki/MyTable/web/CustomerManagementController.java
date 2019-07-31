package com.finki.MyTable.web;

import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.exception.*;
import com.finki.MyTable.service.CustomerManagementService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerManagementController {
    private final CustomerManagementService customerManagementService;

    public CustomerManagementController(CustomerManagementService customerManagementService) {
        this.customerManagementService = customerManagementService;
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable("customerId") Long customerId) throws UnknownCustomerException {
        return customerManagementService.getCustomer(customerId);
    }

    @RequestMapping(value="/customer/login", method = RequestMethod.POST)
    public Customer loginCustomer(@RequestParam("email") String email, @RequestParam("password") String password) throws EmailNotFoundException, IncorrectPasswordException, AccountNotActiveException {
        return customerManagementService.loginCustomer(email, password);
    }

    @RequestMapping(value = "/customer/register", method = RequestMethod.POST)
    public Customer registerCustomer(@RequestParam("firstName") String firstName,
                                     @RequestParam("lastName") String lastName,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password) throws CustomerWithSameEmailExistsException {
        return customerManagementService.registerNewCustomer(firstName,lastName,email,password);
    }

    @RequestMapping(value = "/customer/edit", method = RequestMethod.POST)
    public Customer editCustomer(@RequestParam("id") Long id,
                                     @RequestParam("firstName") String firstName,
                                     @RequestParam("lastName") String lastName) throws UnknownCustomerException {
        return customerManagementService.changeDetailsToCustomer(id,firstName,lastName);
    }

    @RequestMapping(value = "/customer/changePassword", method = RequestMethod.POST)
    public Customer changePassword(@RequestParam("id") Long id,
                                 @RequestParam("newPassword") String newPassword) throws UnknownCustomerException, NoDifferenceInPasswordsException {
        return customerManagementService.changePassword(id,newPassword);
    }

    @RequestMapping(value = "/customer/remove", method = RequestMethod.POST)
    public void removeCustomer(@RequestParam("id") Long id) throws UnknownCustomerException {
        customerManagementService.removeCustomer(id);
    }

}
