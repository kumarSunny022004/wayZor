package com.wayzor.wayzor_backend.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayzor.wayzor_backend.config.GoogleConfig;
import com.wayzor.wayzor_backend.config.RestTemplateConfig;
import com.wayzor.wayzor_backend.dto.DestinationSearchResponse;
import com.wayzor.wayzor_backend.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
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
            String query = "tourist attractions in " + city;
            // Build the Google Places API URL with query params
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://maps.googleapis.com/maps/api/place/textsearch/json")
                    .queryParam("query", query)
                    .queryParam("key", googleConfig.googleApiKey)
                    .toUriString();

            // Make the API call
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Parse the JSON response
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode results = root.path("results");

            List<DestinationSearchResponse> destinations = new ArrayList<>();

            for (JsonNode result : results) {
                String name = result.path("name").asText();
                String address = result.path("formatted_address").asText();
                double lat = result.path("geometry").path("location").path("lat").asDouble();
                double lng = result.path("geometry").path("location").path("lng").asDouble();

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

                destinations.add(new DestinationSearchResponse(name, address, lat, lng, thumbnailUrl));
            }

            return destinations;

        } catch (Exception e) {
            log.error("Error while calling Google Places API", e);
            throw new ApiException("Unable to fetch destinations. Please try again later.");
        }
    }
}
