package com.example.security_service.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security_service.dto.JwtResponse;
import com.example.security_service.dto.RefreshTokenRequest;
import com.example.security_service.entity.RefreshToken;
import com.example.security_service.entity.User;
import com.example.security_service.repository.UserRepository;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public JwtService jwtService;

    // @Autowired
    // private RefreshTokenService refreshTokenService;

    // @Autowired
    // private AuthenticationManager authenticationManager;

    public String saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User saved successfully";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getUsername());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));      
                
    }

}