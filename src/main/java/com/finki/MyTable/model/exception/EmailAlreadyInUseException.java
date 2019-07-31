package com.finki.MyTable.model.exception;

public class EmailAlreadyInUseException extends Exception {
    public EmailAlreadyInUseException() {
        super("Email Already In Use");
    }
}
