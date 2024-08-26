package com.example.blog.service;

import com.example.blog.exception.UserNotFoundException;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetUserByUsername() {
        User user = new User("username", "password", "bio");
        when(userRepository.findByUsername("username")).thenReturn(user);

        User result = userService.getUserByUsername("username");

        assertNotNull(result);
        assertEquals("username", result.getUsername());
        verify(userRepository, times(1)).findByUsername("username");
    }

    @Test
    void testCreateUser() {
        User user = new User("username", "password", "bio");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("username", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void testUpdateUser() {
        User existingUser = new User("username", "password", "bio");
        existingUser.setUserId(1L);
        User userDetails = new User();
        userDetails.setBio("New Bio");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUser(1L, userDetails);

        assertNotNull(result);
        assertEquals("New Bio", result.getBio());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUserNotFound() {
        User userDetails = new User();
        userDetails.setBio("New Bio");

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(1L, userDetails);
        });

        assertEquals("No user found with ID 1", thrown.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).save(any(User.class));
    }
}
