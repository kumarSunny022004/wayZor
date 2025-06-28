package com.wayzor.wayzor_backend.service;

import com.wayzor.wayzor_backend.dto.PackageResponse;
import com.wayzor.wayzor_backend.dto.UserSummaryDto;
import com.wayzor.wayzor_backend.entity.TouristPackage;
import com.wayzor.wayzor_backend.entity.User;
import com.wayzor.wayzor_backend.exception.ApiException;
import com.wayzor.wayzor_backend.repository.TouristPackageRepository;
import com.wayzor.wayzor_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final TouristPackageRepository touristPackageRepository;

    public List<UserSummaryDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(u -> new UserSummaryDto(
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        u.getRole()
                ))
                .collect(Collectors.toList());
    }

    public List<UserSummaryDto> getAllHosts() {
        return userRepository.findByRole("HOST").stream()
                .map(user -> new UserSummaryDto(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                .toList();
    }

    public List<PackageResponse> getAllPackages() {
        List<TouristPackage> packages = touristPackageRepository.findAll();

        return packages.stream()
                .map(p -> new PackageResponse(
                        p.getId(),
                        p.getTitle(),
                        p.getCity(),
                        p.getPrice()
                ))
                .toList();
    }

    public void deletePackage(Long id) {
        TouristPackage touristPackage = touristPackageRepository.findById(id)
                .orElseThrow(() -> new ApiException("Package not found"));

        touristPackageRepository.delete(touristPackage);
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ApiException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public UserSummaryDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found"));

        log.info("Fetched user with id {}", id);

        return new UserSummaryDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }


}
