package com.epam.spring.homework3.exception;

public class EntityExistsException extends ServiceException {

    private static final String DEFAULT_MESSAGE = "Entity already exists!";

    public EntityExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public EntityExistsException(String message) {
        super(message);
    }

}
