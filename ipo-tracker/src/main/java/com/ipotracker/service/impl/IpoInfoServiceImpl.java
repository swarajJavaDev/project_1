package com.ipotracker.service.impl;

import com.ipotracker.dto.IpoInfoDto;
import com.ipotracker.service.IpoInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class IpoInfoServiceImpl implements IpoInfoService {
    
    @Value("${external.api.ipo.base-url}")
    private String baseUrl;
    
    @Value("${external.api.ipo.timeout}")
    private int timeout;
    
    private final RestTemplate restTemplate;
    
    // Mock data for demonstration purposes
    private final Map<String, IpoInfoDto> mockIpoData;
    
    public IpoInfoServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.mockIpoData = initializeMockData();
    }
    
    @Override
    public IpoInfoDto getIpoInfo(String ipoName) {
        try {
            // For demonstration, using mock data
            // In real implementation, this would call external API
            return getMockIpoInfo(ipoName);
            
            // Real API call would look like this:
            // String url = baseUrl + "/ipo-info?name=" + ipoName;
            // return restTemplate.getForObject(url, IpoInfoDto.class);
            
        } catch (Exception e) {
            System.err.println("Failed to fetch IPO info for: " + ipoName + ". Error: " + e.getMessage());
            return createDefaultIpoInfo(ipoName);
        }
    }
    
    @Override
    public String getGmp(String ipoName) {
        try {
            IpoInfoDto ipoInfo = getIpoInfo(ipoName);
            return ipoInfo != null ? ipoInfo.getGmp() : null;
        } catch (Exception e) {
            System.err.println("Failed to fetch GMP for: " + ipoName + ". Error: " + e.getMessage());
            return null;
        }
    }
    
    private IpoInfoDto getMockIpoInfo(String ipoName) {
        String normalizedName = ipoName.toLowerCase().trim();
        
        // Check if we have mock data for this IPO
        IpoInfoDto mockData = mockIpoData.get(normalizedName);
        if (mockData != null) {
            return mockData;
        }
        
        // Return default data if not found
        return createDefaultIpoInfo(ipoName);
    }
    
    private IpoInfoDto createDefaultIpoInfo(String ipoName) {
        IpoInfoDto defaultInfo = new IpoInfoDto();
        defaultInfo.setIpoName(ipoName);
        defaultInfo.setGmp("₹0");
        defaultInfo.setGmpPercentage("0%");
        defaultInfo.setListingDate(LocalDate.now().plusDays(7));
        defaultInfo.setStatus("Active");
        return defaultInfo;
    }
    
    private Map<String, IpoInfoDto> initializeMockData() {
        Map<String, IpoInfoDto> mockData = new HashMap<>();
        
        // Sample IPO data
        IpoInfoDto reliance = new IpoInfoDto();
        reliance.setIpoName("Reliance Industries");
        reliance.setGmp("₹150");
        reliance.setGmpPercentage("12.5%");
        reliance.setListingDate(LocalDate.of(2024, 2, 15));
        reliance.setPrice("₹1200");
        reliance.setLotSize("12");
        reliance.setStatus("Active");
        mockData.put("reliance industries", reliance);
        
        IpoInfoDto tata = new IpoInfoDto();
        tata.setIpoName("Tata Technologies");
        tata.setGmp("₹200");
        tata.setGmpPercentage("15.2%");
        tata.setListingDate(LocalDate.of(2024, 2, 20));
        tata.setPrice("₹1320");
        tata.setLotSize("11");
        tata.setStatus("Active");
        mockData.put("tata technologies", tata);
        
        IpoInfoDto ireda = new IpoInfoDto();
        ireda.setIpoName("IREDA");
        ireda.setGmp("₹35");
        ireda.setGmpPercentage("11.7%");
        ireda.setListingDate(LocalDate.of(2024, 2, 10));
        ireda.setPrice("₹300");
        ireda.setLotSize("50");
        ireda.setStatus("Listed");
        mockData.put("ireda", ireda);
        
        IpoInfoDto bharti = new IpoInfoDto();
        bharti.setIpoName("Bharti Hexacom");
        bharti.setGmp("₹80");
        bharti.setGmpPercentage("14.5%");
        bharti.setListingDate(LocalDate.of(2024, 2, 25));
        bharti.setPrice("₹550");
        bharti.setLotSize("27");
        bharti.setStatus("Active");
        mockData.put("bharti hexacom", bharti);
        
        return mockData;
    }
}