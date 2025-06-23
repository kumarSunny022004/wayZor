package com.wayzor.wayzor_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationSearchResponse {
    private String name;
    private String address;
    private double longitude;
    private double latitude;
    private String thumbnailurl;
}
