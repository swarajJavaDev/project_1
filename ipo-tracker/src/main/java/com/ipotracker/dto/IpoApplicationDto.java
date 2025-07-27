package com.ipotracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ipotracker.entity.IpoApplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class IpoApplicationDto {
    
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String pan;
    
    private String dematId;
    
    @NotBlank(message = "IPO name is required")
    private String ipoName;
    
    @NotNull(message = "Application date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate listingDate;
    
    private IpoApplication.AllotmentStatus allotmentStatus;
    
    private String gmp;
    
    private String username; // For admin view
    
    // Default constructor
    public IpoApplicationDto() {}
    
    // Constructor with parameters
    public IpoApplicationDto(String name, String pan, String dematId, String ipoName, 
                            LocalDate applicationDate) {
        this.name = name;
        this.pan = pan;
        this.dematId = dematId;
        this.ipoName = ipoName;
        this.applicationDate = applicationDate;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPan() {
        return pan;
    }
    
    public void setPan(String pan) {
        this.pan = pan;
    }
    
    public String getDematId() {
        return dematId;
    }
    
    public void setDematId(String dematId) {
        this.dematId = dematId;
    }
    
    public String getIpoName() {
        return ipoName;
    }
    
    public void setIpoName(String ipoName) {
        this.ipoName = ipoName;
    }
    
    public LocalDate getApplicationDate() {
        return applicationDate;
    }
    
    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }
    
    public LocalDate getListingDate() {
        return listingDate;
    }
    
    public void setListingDate(LocalDate listingDate) {
        this.listingDate = listingDate;
    }
    
    public IpoApplication.AllotmentStatus getAllotmentStatus() {
        return allotmentStatus;
    }
    
    public void setAllotmentStatus(IpoApplication.AllotmentStatus allotmentStatus) {
        this.allotmentStatus = allotmentStatus;
    }
    
    public String getGmp() {
        return gmp;
    }
    
    public void setGmp(String gmp) {
        this.gmp = gmp;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}