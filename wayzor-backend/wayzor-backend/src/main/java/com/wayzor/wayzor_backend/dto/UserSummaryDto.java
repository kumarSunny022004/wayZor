package com.wayzor.wayzor_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummaryDto {
    private Long id;
    private String name;
    private String email;
    private String role;
}
