package com.ipotracker.repository;

import com.ipotracker.entity.IpoApplication;
import com.ipotracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IpoApplicationRepository extends JpaRepository<IpoApplication, Long> {
    
    List<IpoApplication> findByUserOrderByApplicationDateDesc(User user);
    
    List<IpoApplication> findByUserAndIpoNameContainingIgnoreCaseOrderByApplicationDateDesc(User user, String ipoName);
    
    @Query("SELECT i FROM IpoApplication i WHERE i.user = :user AND " +
           "(LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(i.pan) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(i.dematId) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(i.ipoName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "ORDER BY i.applicationDate DESC")
    List<IpoApplication> findByUserAndKeyword(@Param("user") User user, @Param("keyword") String keyword);
    
    @Query("SELECT i FROM IpoApplication i WHERE " +
           "(LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(i.pan) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(i.dematId) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(i.ipoName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "ORDER BY i.applicationDate DESC")
    List<IpoApplication> findByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT DISTINCT i.ipoName FROM IpoApplication i ORDER BY i.ipoName")
    List<String> findDistinctIpoNames();
}