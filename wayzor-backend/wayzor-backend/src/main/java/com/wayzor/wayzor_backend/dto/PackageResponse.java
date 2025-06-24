package com.wayzor.wayzor_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageResponse {

    private Long id;
    private String title;
    private String city;
    private double price;
}
