package com.wayzor.wayzor_backend.controller;

import com.wayzor.wayzor_backend.dto.FavoriteDestinationDto;
import com.wayzor.wayzor_backend.service.FavoriteDestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteDestinationController {

    private final FavoriteDestinationService service;

    @PostMapping
    public ResponseEntity<Void> addFavorite(@RequestBody FavoriteDestinationDto dto,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        service.addFavorite(dto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteDestinationDto>> getFavorites(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<FavoriteDestinationDto> favorites = service.getFavorites(userDetails.getUsername());
        return ResponseEntity.ok(favorites);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFavorite(@RequestParam Double lat,
                                               @RequestParam Double lng,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        service.removeFavorite(userDetails.getUsername(), lat, lng);
        return ResponseEntity.noContent().build();
    }
}

