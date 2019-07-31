package com.finki.MyTable.model.exception;

public class ItemWithThatNameAlreadyExistsInMenuException extends Exception {
    public ItemWithThatNameAlreadyExistsInMenuException() {
        super("Item With That Name Already Exists In Menu");
    }
}
