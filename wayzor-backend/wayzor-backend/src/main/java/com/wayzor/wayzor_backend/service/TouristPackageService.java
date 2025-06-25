package com.wayzor.wayzor_backend.service;

import com.wayzor.wayzor_backend.dto.CreatePackageRequest;
import com.wayzor.wayzor_backend.dto.PackageResponse;
import com.wayzor.wayzor_backend.entity.TouristPackage;
import com.wayzor.wayzor_backend.entity.User;
import com.wayzor.wayzor_backend.exception.ApiException;
import com.wayzor.wayzor_backend.repository.TouristPackageRepository;
import com.wayzor.wayzor_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TouristPackageService {

    private final TouristPackageRepository packageRepository;
    private final UserRepository userRepository;

    public PackageResponse createPackage(CreatePackageRequest request, UserDetails userDetails) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User host = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Host user not found"));

        if (!host.getRole().equals("HOST")) {
            throw new ApiException("Only users with HOST role can create packages");
        }

        TouristPackage touristPackage = TouristPackage.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .city(request.getCity())
                .price(request.getPrice())
                .days(request.getDays())
                .nights(request.getNights())
                .hotelName(request.getHotelName())
                .host(host)
                .build();

        TouristPackage saved = packageRepository.save(touristPackage);

        return new PackageResponse(saved.getId(), saved.getTitle(), saved.getCity(), saved.getPrice());
    }


    public List<PackageResponse> getPackagesByHost(UserDetails userDetails) {
        String email = userDetails.getUsername();

        User host = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Host not found"));

        if (!host.getRole().equalsIgnoreCase("HOST")) {
            throw new ApiException("Only hosts can view their packages");
        }

        List<TouristPackage> packages = packageRepository.findByHostId(host.getId());

        return packages.stream()
                .map(pkg -> new PackageResponse(
                        pkg.getId(),
                        pkg.getTitle(),
                        pkg.getCity(),
                        pkg.getPrice()
                ))
                .toList();
    }





    public PackageResponse updatePackage(Long id, CreatePackageRequest request, UserDetails userDetails) {
        String email = userDetails.getUsername();

        User host = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Host not found"));

        TouristPackage touristPackage = packageRepository.findById(id)
                .orElseThrow(() -> new ApiException("Package not found"));

        // Ensure only the creator can update
        if (!Long.valueOf(touristPackage.getHost().getId()).equals(host.getId())) {
            throw new ApiException("You are not authorized to update this package");
        }

        // Update fields
        touristPackage.setTitle(request.getTitle());
        touristPackage.setDescription(request.getDescription());
        touristPackage.setCity(request.getCity());
        touristPackage.setPrice(request.getPrice());
        touristPackage.setDays(request.getDays());
        touristPackage.setNights(request.getNights());
        touristPackage.setHotelName(request.getHotelName());

        TouristPackage updated = packageRepository.save(touristPackage);

        return new PackageResponse(updated.getId(), updated.getTitle(), updated.getCity(), updated.getPrice());
    }




    // TouristPackageService.java

    public void deletePackage(Long id, UserDetails userDetails) {
        String email = userDetails.getUsername();

        User host = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Host not found"));

        TouristPackage touristPackage = packageRepository.findById(id)
                .orElseThrow(() -> new ApiException("Package not found"));

        if (!Long.valueOf(touristPackage.getHost().getId()).equals(host.getId())) {
            throw new ApiException("You are not authorized to delete this package");
        }

        packageRepository.delete(touristPackage);
    }

}
