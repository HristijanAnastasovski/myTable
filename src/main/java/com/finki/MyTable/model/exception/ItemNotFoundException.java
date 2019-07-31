package com.finki.MyTable.model.exception;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException() {
        super("Menu Item Not Found");
    }
}
