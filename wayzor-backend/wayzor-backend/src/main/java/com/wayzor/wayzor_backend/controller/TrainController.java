package com.wayzor.wayzor_backend.controller;

import com.wayzor.wayzor_backend.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @GetMapping
    public ResponseEntity<String> getTrains(
            @RequestParam String from,
            @RequestParam String to
    ) {
        String result = String.valueOf(trainService.getTrainsBetweenStations(from, to));
        return ResponseEntity.ok(result);
    }
}

