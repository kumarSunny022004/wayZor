package com.wayzor.wayzor_backend.controller;


import com.wayzor.wayzor_backend.dto.DestinationAndTrainResponse;
import com.wayzor.wayzor_backend.dto.DestinationSearchResponse;
import com.wayzor.wayzor_backend.dto.HotelResponse;
import com.wayzor.wayzor_backend.dto.TrainInfoDto;
import com.wayzor.wayzor_backend.service.DestinationService;
import com.wayzor.wayzor_backend.service.TrainService;
import com.wayzor.wayzor_backend.util.StationUtil;
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
    private final TrainService trainService;

    // ✅ Search tourist destinations
    @GetMapping("/search")
    public ResponseEntity<List<DestinationSearchResponse>> search(@RequestParam String query) {
        List<DestinationSearchResponse> results = destinationService.searchDestination(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/search/combined")
    public ResponseEntity<DestinationAndTrainResponse> getDestinationsAndTrains(
            @RequestParam String city,
            @RequestParam String fromStation,
            @RequestParam String date
    ) {
        List<DestinationSearchResponse> destinations = destinationService.searchDestination(city);

        String toStation = StationUtil.getStationCode(city);
        List<TrainInfoDto> trains = trainService.getTrainsBetweenStations(fromStation, toStation, date);

        DestinationAndTrainResponse response = new DestinationAndTrainResponse(destinations, trains);
        return ResponseEntity.ok(response);
    }


    // ✅ Get nearby hotels for selected destination
//    http://localhost:8080/api/destinations/nearby-hotels?lat=31.096504&lng=77.2683186
    @GetMapping("/nearby-hotels")
    public ResponseEntity<List<HotelResponse>> getNearbyHotels(
            @RequestParam double lat,
            @RequestParam double lng) {
        List<HotelResponse> hotels = destinationService.getNearbyHotels(lat, lng);
        return ResponseEntity.ok(hotels);
    }
}

