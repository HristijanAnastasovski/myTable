package com.finki.MyTable.model.exception;

public class NoDifferenceInPasswordsException extends Exception {
    public NoDifferenceInPasswordsException() {
        super("No Difference In Passwords");
    }
}
