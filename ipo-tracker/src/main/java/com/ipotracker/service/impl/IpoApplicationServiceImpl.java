package com.ipotracker.service.impl;

import com.ipotracker.dto.IpoApplicationDto;
import com.ipotracker.entity.IpoApplication;
import com.ipotracker.entity.User;
import com.ipotracker.repository.IpoApplicationRepository;
import com.ipotracker.service.IpoApplicationService;
import com.ipotracker.service.IpoInfoService;
import com.ipotracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IpoApplicationServiceImpl implements IpoApplicationService {
    
    private final IpoApplicationRepository ipoApplicationRepository;
    private final UserService userService;
    private final IpoInfoService ipoInfoService;
    
    @Autowired
    public IpoApplicationServiceImpl(IpoApplicationRepository ipoApplicationRepository,
                                   UserService userService,
                                   IpoInfoService ipoInfoService) {
        this.ipoApplicationRepository = ipoApplicationRepository;
        this.userService = userService;
        this.ipoInfoService = ipoInfoService;
    }
    
    @Override
    public IpoApplicationDto createIpoApplication(IpoApplicationDto ipoApplicationDto) {
        User currentUser = userService.getCurrentUser();
        
        IpoApplication ipoApplication = convertToEntity(ipoApplicationDto);
        ipoApplication.setUser(currentUser);
        
        // Try to fetch GMP and listing date from external API
        try {
            String gmp = ipoInfoService.getGmp(ipoApplicationDto.getIpoName());
            if (gmp != null && !gmp.isEmpty()) {
                ipoApplication.setGmp(gmp);
            }
        } catch (Exception e) {
            // Log the error but don't fail the creation
            System.err.println("Failed to fetch GMP for IPO: " + ipoApplicationDto.getIpoName() + ". Error: " + e.getMessage());
        }
        
        IpoApplication savedApplication = ipoApplicationRepository.save(ipoApplication);
        return convertToDto(savedApplication);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<IpoApplicationDto> getUserIpoApplications() {
        User currentUser = userService.getCurrentUser();
        List<IpoApplication> applications = ipoApplicationRepository.findByUserOrderByApplicationDateDesc(currentUser);
        return applications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<IpoApplicationDto> getAllIpoApplications() {
        List<IpoApplication> applications = ipoApplicationRepository.findAll();
        return applications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<IpoApplicationDto> searchUserIpoApplications(String keyword) {
        User currentUser = userService.getCurrentUser();
        List<IpoApplication> applications = ipoApplicationRepository.findByUserAndKeyword(currentUser, keyword);
        return applications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<IpoApplicationDto> searchAllIpoApplications(String keyword) {
        List<IpoApplication> applications = ipoApplicationRepository.findByKeyword(keyword);
        return applications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public IpoApplicationDto convertToDto(IpoApplication ipoApplication) {
        IpoApplicationDto dto = new IpoApplicationDto();
        dto.setId(ipoApplication.getId());
        dto.setName(ipoApplication.getName());
        dto.setPan(ipoApplication.getPan());
        dto.setDematId(ipoApplication.getDematId());
        dto.setIpoName(ipoApplication.getIpoName());
        dto.setApplicationDate(ipoApplication.getApplicationDate());
        dto.setListingDate(ipoApplication.getListingDate());
        dto.setAllotmentStatus(ipoApplication.getAllotmentStatus());
        dto.setGmp(ipoApplication.getGmp());
        dto.setUsername(ipoApplication.getUser().getUsername());
        return dto;
    }
    
    @Override
    public IpoApplication convertToEntity(IpoApplicationDto dto) {
        IpoApplication entity = new IpoApplication();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPan(dto.getPan());
        entity.setDematId(dto.getDematId());
        entity.setIpoName(dto.getIpoName());
        entity.setApplicationDate(dto.getApplicationDate());
        entity.setListingDate(dto.getListingDate());
        entity.setAllotmentStatus(dto.getAllotmentStatus());
        entity.setGmp(dto.getGmp());
        return entity;
    }
}