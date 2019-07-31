package com.finki.MyTable.model.exception;

public class AccountNotActiveException extends Exception {
    public AccountNotActiveException(){
        super("Account is not activated");
    }
}
