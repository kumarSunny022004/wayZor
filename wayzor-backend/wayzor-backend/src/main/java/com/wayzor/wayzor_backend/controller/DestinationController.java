package com.wayzor.wayzor_backend.controller;


import com.wayzor.wayzor_backend.dto.DestinationSearchResponse;
import com.wayzor.wayzor_backend.dto.HotelResponse;
import com.wayzor.wayzor_backend.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;

    // ✅ Search tourist destinations
    @GetMapping("/search")
    public ResponseEntity<List<DestinationSearchResponse>> search(@RequestParam String query) {
        List<DestinationSearchResponse> results = destinationService.searchDestination(query);
        return ResponseEntity.ok(results);
    }

    // ✅ Get nearby hotels for selected destination
    @GetMapping("/nearby-hotels")
    public ResponseEntity<List<HotelResponse>> getNearbyHotels(
            @RequestParam double lat,
            @RequestParam double lng) {
        List<HotelResponse> hotels = destinationService.getNearbyHotels(lat, lng);
        return ResponseEntity.ok(hotels);
    }
}

