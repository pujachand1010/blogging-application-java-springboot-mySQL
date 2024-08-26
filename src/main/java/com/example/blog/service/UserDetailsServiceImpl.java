package com.example.blog.service;

import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementation of UserDetailsService to load user specific data during authentication.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;  // Repository for accessing user data

    /**
     * Loads a user by username from the repository.
     * throws UsernameNotFoundException if no user is found with the provided username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);                // Fetch user from the repository by username
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);       // If user not found, throw an exception
        }

        return new org.springframework.security.core.userdetails.User(              // Return UserDetails object with user's credentials and authorities
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList() // Authorities (empty list as roles are not used)
        );
    }
}
