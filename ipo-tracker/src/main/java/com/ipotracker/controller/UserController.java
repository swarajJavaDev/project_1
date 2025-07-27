package com.ipotracker.controller;

import com.ipotracker.dto.IpoApplicationDto;
import com.ipotracker.service.IpoApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {
    
    private final IpoApplicationService ipoApplicationService;
    
    @Autowired
    public UserController(IpoApplicationService ipoApplicationService) {
        this.ipoApplicationService = ipoApplicationService;
    }
    
    @PostMapping("/ipo")
    public ResponseEntity<Map<String, Object>> createIpoApplication(@Valid @RequestBody IpoApplicationDto ipoApplicationDto) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            IpoApplicationDto createdApplication = ipoApplicationService.createIpoApplication(ipoApplicationDto);
            
            response.put("success", true);
            response.put("message", "IPO application created successfully");
            response.put("data", createdApplication);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to create IPO application: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/ipo")
    public ResponseEntity<Map<String, Object>> getUserIpoApplications() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<IpoApplicationDto> applications = ipoApplicationService.getUserIpoApplications();
            
            response.put("success", true);
            response.put("message", "IPO applications retrieved successfully");
            response.put("data", applications);
            response.put("count", applications.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to retrieve IPO applications: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}