package com.ipotracker.service;

import com.ipotracker.dto.IpoInfoDto;

public interface IpoInfoService {
    
    IpoInfoDto getIpoInfo(String ipoName);
    
    String getGmp(String ipoName);
}