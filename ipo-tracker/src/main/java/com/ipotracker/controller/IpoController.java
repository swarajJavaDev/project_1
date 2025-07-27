package com.ipotracker.controller;

import com.ipotracker.dto.IpoApplicationDto;
import com.ipotracker.dto.IpoInfoDto;
import com.ipotracker.service.IpoApplicationService;
import com.ipotracker.service.IpoInfoService;
import com.ipotracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ipo")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class IpoController {
    
    private final IpoApplicationService ipoApplicationService;
    private final IpoInfoService ipoInfoService;
    private final UserService userService;
    
    @Autowired
    public IpoController(IpoApplicationService ipoApplicationService,
                        IpoInfoService ipoInfoService,
                        UserService userService) {
        this.ipoApplicationService = ipoApplicationService;
        this.ipoInfoService = ipoInfoService;
        this.userService = userService;
    }
    
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchIpoApplications(@RequestParam String keyword) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<IpoApplicationDto> applications;
            boolean isAdmin = userService.getCurrentUser().getRole().name().equals("ADMIN");
            
            if (isAdmin) {
                applications = ipoApplicationService.searchAllIpoApplications(keyword);
            } else {
                applications = ipoApplicationService.searchUserIpoApplications(keyword);
            }
            
            response.put("success", true);
            response.put("message", "Search completed successfully");
            response.put("data", applications);
            response.put("count", applications.size());
            response.put("keyword", keyword);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Search failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/gmp/{ipoName}")
    public ResponseEntity<Map<String, Object>> getIpoGmp(@PathVariable String ipoName) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            IpoInfoDto ipoInfo = ipoInfoService.getIpoInfo(ipoName);
            
            response.put("success", true);
            response.put("message", "IPO information retrieved successfully");
            response.put("data", ipoInfo);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to retrieve IPO information: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/gmp")
    public ResponseEntity<Map<String, Object>> getIpoGmpByQuery(@RequestParam String name) {
        return getIpoGmp(name);
    }
}