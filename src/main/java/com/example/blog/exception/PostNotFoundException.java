package com.example.blog.exception;


/* Custom exception class for when we ty to access a resource such as post which doesn't exist */

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
