package com.wayzor.wayzor_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleChangeResponse {
    private Long userId;
    private String oldRole;
    private String newRole;
    private String message;
}

