package com.example.blog.service;


import com.example.blog.model.User;


public interface UserService {

    User getUserByUsername(String username);
    User createUser(User user);
    User updateUser(Long id, User userDetails);

}
