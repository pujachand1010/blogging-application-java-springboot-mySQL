package com.example.blog.exception;

/* Custom exception class for when we ty to access a resource such as tag which doesn't exist */

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(String message) {
        super(message);
    }
}
