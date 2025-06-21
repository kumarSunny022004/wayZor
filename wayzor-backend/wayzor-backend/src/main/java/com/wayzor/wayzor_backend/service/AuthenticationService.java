package com.wayzor.wayzor_backend.service;


import com.wayzor.wayzor_backend.dto.AuthResponse;
import com.wayzor.wayzor_backend.dto.RegisterRequest;
import com.wayzor.wayzor_backend.entity.User;
import com.wayzor.wayzor_backend.repository.UserRepository;
import com.wayzor.wayzor_backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request){
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}