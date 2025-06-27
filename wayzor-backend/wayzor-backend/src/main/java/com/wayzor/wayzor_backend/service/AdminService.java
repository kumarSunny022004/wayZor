package com.wayzor.wayzor_backend.service;

import com.wayzor.wayzor_backend.dto.UserSummaryDto;
import com.wayzor.wayzor_backend.entity.User;
import com.wayzor.wayzor_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<UserSummaryDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(u -> new UserSummaryDto(
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        u.getRole()
                ))
                .collect(Collectors.toList());
    }
}
