package com.finki.MyTable.model.exception;

public class CustomerWithSameEmailExistsException extends Exception {
    public CustomerWithSameEmailExistsException() {
        super("Customer With Same Email Already Exists");
    }
}
