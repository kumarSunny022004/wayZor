package com.wayzor.wayzor_backend.controller;

import com.wayzor.wayzor_backend.dto.AdminDashboardStats;
import com.wayzor.wayzor_backend.dto.PackageResponse;
import com.wayzor.wayzor_backend.dto.RoleChangeResponse;
import com.wayzor.wayzor_backend.dto.UserSummaryDto;
import com.wayzor.wayzor_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
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

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/users/{id}")
    public ResponseEntity<UserSummaryDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }


    @PutMapping("/users/{userId}/role")
    public ResponseEntity<RoleChangeResponse> updateUserRole(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.changeUserRole(userId));
    }


    @GetMapping("/dashboard-stats")
    public ResponseEntity<AdminDashboardStats> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

}
