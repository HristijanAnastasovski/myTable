package com.finki.MyTable.model.exception;

public class EarlyOrderDateException extends Exception {
    public EarlyOrderDateException() {
        super("Your order can be in a maximum of 1 month in the future");
    }
}
