package com.ipotracker.config;

import com.ipotracker.entity.User;
import com.ipotracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        initializeDefaultAdmin();
        initializeTestUser();
    }
    
    private void initializeDefaultAdmin() {
        String adminUsername = "admin";
        
        if (!userRepository.existsByUsername(adminUsername)) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            
            userRepository.save(admin);
            System.out.println("Default admin user created: " + adminUsername + " / admin123");
        }
    }
    
    private void initializeTestUser() {
        String testUsername = "testuser";
        
        if (!userRepository.existsByUsername(testUsername)) {
            User testUser = new User();
            testUser.setUsername(testUsername);
            testUser.setPassword(passwordEncoder.encode("test123"));
            testUser.setRole(User.Role.USER);
            
            userRepository.save(testUser);
            System.out.println("Test user created: " + testUsername + " / test123");
        }
    }
}