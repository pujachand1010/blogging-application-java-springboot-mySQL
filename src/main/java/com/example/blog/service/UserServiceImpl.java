package com.example.blog.service;

import com.example.blog.exception.UserNotFoundException;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createUser(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("User details must not be null");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with ID " + id));

        if (userDetails.getBio() != null) {
            user.setBio(userDetails.getBio());
        }

        if (userDetails.getBio() != null) {
            user.setBio(userDetails.getBio());
        }

        return userRepository.save(user);
    }

}

