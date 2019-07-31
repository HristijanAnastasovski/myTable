package com.finki.MyTable.model.exception;

public class RestaurantAlreadyExistsException extends Exception {
    public RestaurantAlreadyExistsException() {
        super("Restaurant Already Exists");
    }
}
