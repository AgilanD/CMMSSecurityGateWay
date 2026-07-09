package com.example.gateway.controller;

import com.example.gateway.aspect.AuditLoggable;
import com.example.gateway.common.dto.AuditLogsRequestDto;
import com.example.gateway.dto.UsersRequestDto;
import com.example.gateway.service.AuthService;
import com.example.gateway.security.JwtUtils;
import com.example.gateway.usercontext.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsersRequestDto user, HttpServletRequest request) {
        String token = jwtUtils.getJwtFromCookies(request);

        if (token != null && jwtUtils.validateToken(token)) {
            String extractedUserId = jwtUtils.getUserIdFromToken(token);
            UserContext.setUserId(Long.parseLong(extractedUserId));
        } else {
            UserContext.clear();
        }
        authService.registerUser(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.LOGIN_SUCCESS, tableName = "users")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        ResponseCookie jwtCookie = authService.authenticateAndBuildCookie(username, password);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Authentication successful. Secure cookie established.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie deleteCookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body("Cookie cleared out successfully.");
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String token) {
        return ResponseEntity.ok(authService.validateTokenString(token));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UsersRequestDto updater) {
        authService.updateUser(id, updater);
        return ResponseEntity.ok("SuccessFully Update the User");
    }
}
