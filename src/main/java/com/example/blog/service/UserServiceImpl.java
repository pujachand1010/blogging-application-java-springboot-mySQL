package com.example.blog.service;

import com.example.blog.exception.UserNotFoundException;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;  // Repository for accessing user data

    @Autowired
    private PasswordEncoder passwordEncoder;  // Encoder for password encryption

    /** Retrieves a user by their username. */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Creates a new user and saves it to the repository.
     * throws IllegalArgumentException if the username or password is null
     */
    @Override
    public User createUser(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {                         // Validate that username and password are not null
            throw new IllegalArgumentException("Username and password cannot be null");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));                   // Encode the password before saving
        return userRepository.save(user);
    }

    /**
     * Updates an existing user with new details.
     * throws IllegalArgumentException if the provided userDetails is null
     * throws UserNotFoundException if no user is found with the provided ID
     */
    @Override
    @Transactional
    public User updateUser(Long id, User userDetails) {
        // Validate that userDetails is not null
        if (userDetails == null) {
            throw new IllegalArgumentException("User details must not be null");
        }

        User user = userRepository.findById(id)                                     // Fetch the user from the repository by ID, throw an exception if not found
                .orElseThrow(() -> new UserNotFoundException("No user found with ID " + id));

        if (userDetails.getBio() != null) {                             // Update user fields if new values are provided
            user.setBio(userDetails.getBio());
        }

        return userRepository.save(user);
    }
}
