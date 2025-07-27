package com.ipotracker.controller;

import com.ipotracker.dto.IpoApplicationDto;
import com.ipotracker.service.IpoApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final IpoApplicationService ipoApplicationService;
    
    @Autowired
    public AdminController(IpoApplicationService ipoApplicationService) {
        this.ipoApplicationService = ipoApplicationService;
    }
    
    @GetMapping("/ipo")
    public ResponseEntity<Map<String, Object>> getAllIpoApplications() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<IpoApplicationDto> applications = ipoApplicationService.getAllIpoApplications();
            
            response.put("success", true);
            response.put("message", "All IPO applications retrieved successfully");
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