package com.wayzor.wayzor_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDestinationDto {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String thumbnailUrl;
}
