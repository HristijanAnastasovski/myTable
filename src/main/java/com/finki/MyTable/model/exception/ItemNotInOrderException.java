package com.finki.MyTable.model.exception;

public class ItemNotInOrderException extends Exception {
    public ItemNotInOrderException() {
        super("Menu Item Not In Order");
    }
}
