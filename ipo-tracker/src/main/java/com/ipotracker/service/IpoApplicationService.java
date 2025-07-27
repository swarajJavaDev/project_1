package com.ipotracker.service;

import com.ipotracker.dto.IpoApplicationDto;
import com.ipotracker.entity.IpoApplication;

import java.util.List;

public interface IpoApplicationService {
    
    IpoApplicationDto createIpoApplication(IpoApplicationDto ipoApplicationDto);
    
    List<IpoApplicationDto> getUserIpoApplications();
    
    List<IpoApplicationDto> getAllIpoApplications(); // Admin only
    
    List<IpoApplicationDto> searchUserIpoApplications(String keyword);
    
    List<IpoApplicationDto> searchAllIpoApplications(String keyword); // Admin only
    
    IpoApplicationDto convertToDto(IpoApplication ipoApplication);
    
    IpoApplication convertToEntity(IpoApplicationDto ipoApplicationDto);
}