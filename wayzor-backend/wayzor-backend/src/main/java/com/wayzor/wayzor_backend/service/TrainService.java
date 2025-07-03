package com.wayzor.wayzor_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wayzor.wayzor_backend.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainService {

    @Value("${rapidapi.host}")
    private String host;

    @Value("${rapidapi.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public JsonNode getTrainsBetweenStations(String fromCode, String toCode) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://" + host + "/api/v3/trainBetweenStations")
                    .queryParam("fromStationCode", fromCode)
                    .queryParam("toStationCode", toCode)
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-rapidapi-host", host);
            headers.set("x-rapidapi-key", apiKey);

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return objectMapper.readTree(response.getBody());

        } catch (Exception e) {
            log.error("Error while calling RapidAPI Train Service", e);
            throw new ApiException("Unable to fetch trains. Please try again later.");
        }
    }
}
