package com.wayzor.wayzor_backend.dto;

import com.wayzor.wayzor_backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    private String email;
    private String name;
    private String password;
    private Role role;
}

