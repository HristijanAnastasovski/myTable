package com.finki.MyTable.service.implementation;

import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.CustomerOrder;
import com.finki.MyTable.model.exception.*;
import com.finki.MyTable.model.factory.CustomerFactory;
import com.finki.MyTable.repository.CustomerOrderRepository;
import com.finki.MyTable.repository.CustomerRepository;
import com.finki.MyTable.service.CustomerManagementService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import java.util.List;

@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {
    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomerManagementServiceImpl(CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository) {
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    @Override
    public Customer registerNewCustomer(String firstName, String lastName, String email, String password) throws CustomerWithSameEmailExistsException {
        if(customerRepository.findByEmail(email).isPresent())
            throw new CustomerWithSameEmailExistsException();
        Customer customer = CustomerFactory.create(firstName,lastName,email,password);
        return customerRepository.save(customer);

    }


    @Override
    public Customer changeDetailsToCustomer(Long id, String firstName, String lastName) throws UnknownCustomerException {
        Customer customer = customerRepository.findById(id).orElseThrow(UnknownCustomerException::new);
        customer.firstName = firstName;
        customer.lastName = lastName;

        return customerRepository.save(customer);
    }

    @Override
    public Customer changePassword(Long id, String newPassword) throws UnknownCustomerException, NoDifferenceInPasswordsException {
        Customer customer = customerRepository.findById(id).orElseThrow(UnknownCustomerException::new);
        String password = passwordEncoder.encode(newPassword);
        if(customer.password.equals(password))
            throw new NoDifferenceInPasswordsException();
        customer.password = password;
        return customerRepository.save(customer);

    }

    @Override
    public void removeCustomer(Long id) throws UnknownCustomerException {
        Customer customer = customerRepository.findById(id).orElseThrow(UnknownCustomerException::new);
        customerRepository.delete(customer);

    }



    @Override
    public Customer getCustomer(Long id) throws UnknownCustomerException {
        return customerRepository.findById(id).orElseThrow(UnknownCustomerException::new);
    }


    @Override
    public Customer loginCustomer(String email, String password) throws EmailNotFoundException, IncorrectPasswordException, AccountNotActiveException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        if(!customer.isActive)
            throw new AccountNotActiveException();
        if(passwordEncoder.matches(password,customer.password))
            return customer;
        else
            throw new IncorrectPasswordException();
    }
}
