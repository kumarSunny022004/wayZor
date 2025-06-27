package com.wayzor.wayzor_backend.controller;

import com.wayzor.wayzor_backend.dto.CreatePackageRequest;
import com.wayzor.wayzor_backend.dto.PackageResponse;
import com.wayzor.wayzor_backend.service.TouristPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class TouristPackageController {
    private final TouristPackageService touristPackageService;

    @PostMapping("/add")
    public ResponseEntity<PackageResponse> createPackage(
            @RequestBody CreatePackageRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        PackageResponse response = touristPackageService.createPackage(request, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/mine")
    public ResponseEntity<List<PackageResponse>> getMyPackages(@AuthenticationPrincipal UserDetails userDetails) {
        List<PackageResponse> packages = touristPackageService.getPackagesByHost(userDetails);
        return ResponseEntity.ok(packages);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PackageResponse> updatePackage(
            @PathVariable Long id,
            @RequestBody CreatePackageRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        PackageResponse updated = touristPackageService.updatePackage(id, request, userDetails);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePackage(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        touristPackageService.deletePackage(id, userDetails);
        return ResponseEntity.ok("Package deleted successfully");
    }


    @GetMapping("/city")
//    http://localhost:8080/api/packages/city?name=Shimla
    public ResponseEntity<List<PackageResponse>> getPackagesByCity(@RequestParam("name") String cityName) {
        List<PackageResponse> responses = touristPackageService.getPackagesByCity(cityName);
        return ResponseEntity.ok(responses);
    }
}
