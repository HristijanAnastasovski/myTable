package com.finki.MyTable.model.exception;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException() {
        super("Order Not Found");
    }
}
