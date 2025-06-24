package com.wayzor.wayzor_backend.controller;

import com.wayzor.wayzor_backend.dto.CreatePackageRequest;
import com.wayzor.wayzor_backend.dto.PackageResponse;
import com.wayzor.wayzor_backend.service.TouristPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class TouristPackageController {
    private final TouristPackageService touristPackageService;

    @PostMapping
    public ResponseEntity<PackageResponse> createPackage(
            @RequestBody CreatePackageRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        PackageResponse response = touristPackageService.createPackage(request, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
