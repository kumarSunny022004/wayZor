package com.wayzor.wayzor_backend.controller;

import com.wayzor.wayzor_backend.dto.PackageResponse;
import com.wayzor.wayzor_backend.dto.UserSummaryDto;
import com.wayzor.wayzor_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserSummaryDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/hosts")
    public ResponseEntity<List<UserSummaryDto>> getAllHosts() {
        return ResponseEntity.ok(adminService.getAllHosts());
    }

    @GetMapping("/packages")
    public ResponseEntity<List<PackageResponse>> getAllPackages() {
        return ResponseEntity.ok(adminService.getAllPackages());
    }

    @DeleteMapping("/packages/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        adminService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }

}
