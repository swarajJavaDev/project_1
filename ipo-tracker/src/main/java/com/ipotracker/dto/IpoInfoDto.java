package com.ipotracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class IpoInfoDto {
    
    private String ipoName;
    private String gmp;
    private String gmpPercentage;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate listingDate;
    
    private String price;
    private String lotSize;
    private String status;
    
    // Default constructor
    public IpoInfoDto() {}
    
    // Constructor with parameters
    public IpoInfoDto(String ipoName, String gmp, LocalDate listingDate) {
        this.ipoName = ipoName;
        this.gmp = gmp;
        this.listingDate = listingDate;
    }
    
    // Getters and Setters
    public String getIpoName() {
        return ipoName;
    }
    
    public void setIpoName(String ipoName) {
        this.ipoName = ipoName;
    }
    
    public String getGmp() {
        return gmp;
    }
    
    public void setGmp(String gmp) {
        this.gmp = gmp;
    }
    
    public String getGmpPercentage() {
        return gmpPercentage;
    }
    
    public void setGmpPercentage(String gmpPercentage) {
        this.gmpPercentage = gmpPercentage;
    }
    
    public LocalDate getListingDate() {
        return listingDate;
    }
    
    public void setListingDate(LocalDate listingDate) {
        this.listingDate = listingDate;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getLotSize() {
        return lotSize;
    }
    
    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}