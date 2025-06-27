package com.wayzor.wayzor_backend.repository;

import com.wayzor.wayzor_backend.entity.FavoriteDestination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteDestinationRepository extends JpaRepository<FavoriteDestination, Long> {
    List<FavoriteDestination> findByUserId(Long userId);
    boolean existsByUserIdAndLatitudeAndLongitude(Long userId, Double latitude, Double longitude);
    void deleteByUserIdAndLatitudeAndLongitude(Long userId, Double latitude, Double longitude);
}
