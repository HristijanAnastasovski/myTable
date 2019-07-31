package com.finki.MyTable.model.factory;


import com.finki.MyTable.model.Customer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

public class CustomerFactory {
    public static Customer create(String firstName, String lastName, String email, String password){
        Customer customer = new Customer();
        customer.firstName = firstName;
        customer.lastName = lastName;
        customer.email = email;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        customer.password = passwordEncoder.encode(password);
        customer.isActive = true;

        return customer;
    }
}
