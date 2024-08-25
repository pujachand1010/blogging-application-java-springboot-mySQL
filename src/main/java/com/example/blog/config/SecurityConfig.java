package com.example.blog.config;

import com.example.blog.service.UserDetailsServiceImpl;

import com.example.blog.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/posts/**").authenticated() // Requires authentication for /posts/** endpoints
                        .requestMatchers("/login", "/logout").permitAll() // Allows access to login and logout pages without authentication
                        .anyRequest().permitAll() // Allows all other requests without authentication
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page URL
                        .permitAll() // Allows access to the login page without authentication
                )
                .logout(LogoutConfigurer::permitAll // Allows access to logout functionality without authentication
                );


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}