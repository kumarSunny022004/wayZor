package com.wayzor.wayzor_backend.service;


import com.wayzor.wayzor_backend.dto.AuthResponse;
import com.wayzor.wayzor_backend.dto.LoginRequest;
import com.wayzor.wayzor_backend.dto.RegisterRequest;
import com.wayzor.wayzor_backend.entity.User;
import com.wayzor.wayzor_backend.enums.Role;
import com.wayzor.wayzor_backend.exception.CustomException;
import com.wayzor.wayzor_backend.repository.UserRepository;
import com.wayzor.wayzor_backend.security.JwtUtil;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Enumerated(EnumType.STRING)
    private Role role;

    public AuthResponse register(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already registered with this email");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(String.valueOf(Role.USER))
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }


    public AuthResponse login(LoginRequest request) {
        User user  = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->{
                    log.warn("Login failed: User not found {}",request.getEmail());
                    return new CustomException("Username not found with the given email: "+request.getEmail());
                });
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            log.warn("Login failed - invalid credentials for user: {}", request.getEmail());
            throw new CustomException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(request.getEmail());
        log.info("Login successful for email: {}", request.getEmail());

        return new AuthResponse(token);
    }
}