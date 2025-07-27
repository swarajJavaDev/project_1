package com.ipotracker.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ipo_applications")
public class IpoApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;
    
    @Column(name = "pan_number")
    private String pan;
    
    @Column(name = "demat_id")
    private String dematId;
    
    @NotBlank(message = "IPO name is required")
    @Column(name = "ipo_name", nullable = false)
    private String ipoName;
    
    @NotNull(message = "Application date is required")
    @Column(name = "application_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationDate;
    
    @Column(name = "listing_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate listingDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "allotment_status")
    private AllotmentStatus allotmentStatus = AllotmentStatus.PENDING;
    
    @Column(name = "gmp")
    private String gmp;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // Default constructor
    public IpoApplication() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor with parameters
    public IpoApplication(String name, String pan, String dematId, String ipoName, 
                         LocalDate applicationDate, User user) {
        this();
        this.name = name;
        this.pan = pan;
        this.dematId = dematId;
        this.ipoName = ipoName;
        this.applicationDate = applicationDate;
        this.user = user;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
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
    
    public AllotmentStatus getAllotmentStatus() {
        return allotmentStatus;
    }
    
    public void setAllotmentStatus(AllotmentStatus allotmentStatus) {
        this.allotmentStatus = allotmentStatus;
    }
    
    public String getGmp() {
        return gmp;
    }
    
    public void setGmp(String gmp) {
        this.gmp = gmp;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public enum AllotmentStatus {
        PENDING,
        ALLOTTED,
        NOT_ALLOTTED,
        REFUND_INITIATED,
        CANCELLED
    }
}