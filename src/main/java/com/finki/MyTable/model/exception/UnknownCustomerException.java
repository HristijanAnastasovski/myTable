package com.finki.MyTable.model.exception;

public class UnknownCustomerException extends Exception {
    public UnknownCustomerException() {
        super("Unknown Customer");
    }
}
