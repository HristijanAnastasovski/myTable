package com.finki.MyTable.service;

import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.CustomerOrder;
import com.finki.MyTable.model.exception.*;

import java.util.List;

public interface CustomerManagementService {
    Customer registerNewCustomer(String firstName, String lastName, String email, String password) throws CustomerWithSameEmailExistsException;
    Customer loginCustomer(String email, String password) throws EmailNotFoundException, IncorrectPasswordException, AccountNotActiveException;
    Customer changeDetailsToCustomer(Long id, String firstName, String lastName) throws UnknownCustomerException;
    Customer changePassword(Long id, String newPassword) throws UnknownCustomerException, NoDifferenceInPasswordsException;
    void removeCustomer(Long id) throws UnknownCustomerException;
    Customer getCustomer(Long id) throws UnknownCustomerException;

}
