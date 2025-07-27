package com.ipotracker.service;

import com.ipotracker.dto.UserRegistrationDto;
import com.ipotracker.entity.User;

import java.util.Optional;

public interface UserService {
    
    User registerUser(UserRegistrationDto registrationDto);
    
    Optional<User> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    User getCurrentUser();
}