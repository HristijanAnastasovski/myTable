package com.finki.MyTable.model.exception;

public class OrderAlreadyExistsException extends Exception {
    public OrderAlreadyExistsException() {
        super("Order Already Exists");
    }
}
