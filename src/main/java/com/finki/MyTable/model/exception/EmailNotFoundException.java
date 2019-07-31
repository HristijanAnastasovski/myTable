package com.finki.MyTable.model.exception;

public class EmailNotFoundException extends Exception {
    public EmailNotFoundException() {
        super("Email Not Found");
    }
}
