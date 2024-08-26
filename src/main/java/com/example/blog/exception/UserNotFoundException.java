package com.example.blog.exception;

/* Custom exception class for when we ty to access a resource such as user which doesn't exist */

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
