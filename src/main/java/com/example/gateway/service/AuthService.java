package com.example.gateway.service;

import com.example.gateway.dto.UsersRequestDto;
import org.springframework.http.ResponseCookie;

public interface AuthService {
    void registerUser(UsersRequestDto requestDto);
    ResponseCookie authenticateAndBuildCookie(String username, String password);
    boolean validateTokenString(String token);
    void updateUser(Long id, UsersRequestDto requestDto);
}
