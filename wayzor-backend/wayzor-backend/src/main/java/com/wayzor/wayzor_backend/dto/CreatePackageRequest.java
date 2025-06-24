package com.wayzor.wayzor_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePackageRequest {

    private String title;
    private String description;
    private String city;
    private double price;
    private int days;
    private int nights;
    private String hotelName;
}
