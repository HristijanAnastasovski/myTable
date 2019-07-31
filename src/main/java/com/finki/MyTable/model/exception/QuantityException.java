package com.finki.MyTable.model.exception;

public class QuantityException extends Exception {
    public QuantityException(String itemName) {
        super("Requested quantity of "+itemName+" not available.");
    }
}
