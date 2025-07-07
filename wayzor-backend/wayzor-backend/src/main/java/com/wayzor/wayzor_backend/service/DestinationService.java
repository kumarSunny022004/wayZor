package com.wayzor.wayzor_backend.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayzor.wayzor_backend.config.GoogleConfig;
import com.wayzor.wayzor_backend.config.RestTemplateConfig;
import com.wayzor.wayzor_backend.dto.DestinationSearchResponse;
import com.wayzor.wayzor_backend.dto.HotelResponse;
import com.wayzor.wayzor_backend.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DestinationService {

    private final GoogleConfig googleConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public List<DestinationSearchResponse> searchDestination(String city) {
        try {
            if (googleConfig.googleApiKey == null || googleConfig.googleApiKey.isBlank()) {
                throw new IllegalStateException("Google API key is not configured.");
            }

            String query = "tourist places " + city;
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://maps.googleapis.com/maps/api/place/textsearch/json")
                    .queryParam("query", query)
                    .queryParam("key", googleConfig.googleApiKey)
                    .toUriString();

            log.info("Google Places API URL: {}", url);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            log.info("Google API response: {}", response.getBody());

            JsonNode root = objectMapper.readTree(response.getBody());
            String status = root.path("status").asText();

            if (!"OK".equals(status)) {
                log.error("Google API Error. Status: {}, Message: {}", status, root.path("error_message").asText());
                return Collections.emptyList();
            }

            JsonNode results = root.path("results");
            List<DestinationSearchResponse> destinations = new ArrayList<>();

            for (JsonNode result : results) {
                String name = result.path("name").asText();
                String address = result.path("formatted_address").asText();
                double lat = result.path("geometry").path("location").path("lat").asDouble();
                double lng = result.path("geometry").path("location").path("lng").asDouble();

                String thumbnailUrl = null;
                JsonNode photos = result.path("photos");
                if (photos.isArray() && photos.size() > 0) {
                    String photoRef = photos.get(0).path("photo_reference").asText();
                    thumbnailUrl = UriComponentsBuilder
                            .fromHttpUrl("https://maps.googleapis.com/maps/api/place/photo")
                            .queryParam("maxwidth", "400")
                            .queryParam("photoreference", photoRef)
                            .queryParam("key", googleConfig.googleApiKey)
                            .toUriString();
                }

                destinations.add(new DestinationSearchResponse(name, address, lat, lng, thumbnailUrl));
            }

            return destinations;

        } catch (Exception e) {
            log.error("Error while calling Google Places Text Search API", e);
            throw new ApiException("Unable to fetch destinations: " + e.getMessage());
        }
    }


    public List<HotelResponse> getNearbyHotels(double lat, double lng) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
                    .queryParam("location", lat + "," + lng)
                    .queryParam("radius", "2000")
                    .queryParam("type", "lodging")
                    .queryParam("key", googleConfig.googleApiKey)
                    .toUriString();

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode results = root.path("results");

            List<HotelResponse> hotels = new ArrayList<>();

            for (JsonNode result : results) {
                String name = result.path("name").asText();
                String address = result.path("vicinity").asText();
                double rating = result.path("rating").asDouble(0.0);

                String thumbnailUrl = null;
                JsonNode photos = result.path("photos");
                if (photos.isArray() && !photos.isEmpty()) {
                    String photoRef = photos.get(0).path("photo_reference").asText();
                    thumbnailUrl = UriComponentsBuilder
                            .fromHttpUrl("https://maps.googleapis.com/maps/api/place/photo")
                            .queryParam("maxwidth", "400")
                            .queryParam("photoreference", photoRef)
                            .queryParam("key", googleConfig.googleApiKey)
                            .toUriString();
                }

                hotels.add(new HotelResponse(name, address, rating, thumbnailUrl));
            }

            return hotels;

        } catch (Exception e) {
            log.error("Failed to fetch nearby hotels for location: {}, {}", lat, lng, e);
            throw new ApiException("Unable to fetch nearby hotels. Please try again later.");
        }
    }

}
