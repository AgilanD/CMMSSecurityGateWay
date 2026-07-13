package com.example.gateway.controller;

import com.example.gateway.clients.UserFeignClientSystem;
import com.example.gateway.common.dto.AuditLogsRequestDto;
import com.example.gateway.dto.UsersRequestDto;
import com.example.gateway.entity.Users;
import com.example.gateway.repository.UserRepository;
import com.example.gateway.service.AuthService;
import com.example.gateway.security.JwtUtils;
import com.example.gateway.usercontext.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final UserFeignClientSystem userFeignClientSystem;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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
public ResponseEntity<String> login(
        @RequestParam String username,
        @RequestParam String password) {

    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes == null) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request attributes mapping failed.");
    }

    HttpServletRequest request = attributes.getRequest();
    String ipAddress = request.getRemoteAddr();
    String userIdHeader = request.getHeader("X-User-Id");
    Long performedById = (userIdHeader != null) ? Long.parseLong(userIdHeader) : 0L;

    Optional<Users> matchedUser = userRepository.findByUsername(username);

    Users authenticatedUser = matchedUser
            .filter(u -> passwordEncoder.matches(password, u.getPassword()))
            .orElse(null);

    if (authenticatedUser == null) {
        log.warn("Login failure: Credentials did not match for username '{}'", username);

        AuditLogsRequestDto systemFailureLog = AuditLogsRequestDto.builder()
                .tableName("users")
                .recordId(0L)
                .action(AuditLogsRequestDto.AuditAction.LOGIN_FAILURE)
                .changedData(String.format("{\"message\": \"Failed login attempt for username '%s'. Invalid credentials.\"}", username))
                .performedById(performedById)
                .ipAddress(ipAddress)
                .build();
        userFeignClientSystem.createAuditLog(systemFailureLog);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
    }

    Long dynamicUserId = authenticatedUser.getId();
    ResponseCookie jwtCookie = authService.authenticateAndBuildCookie(username, password);
    String jsonChangedData = String.format("{\"message\": \"User '%s' (ID: %d) successfully logged in.\"}", username, dynamicUserId);

    AuditLogsRequestDto auditLog = AuditLogsRequestDto.builder()
            .tableName("users")
            .recordId(dynamicUserId)
            .action(AuditLogsRequestDto.AuditAction.LOGIN_SUCCESS)
            .changedData(jsonChangedData)
            .performedById(performedById != 0L ? performedById : dynamicUserId)
            .ipAddress(ipAddress)
            .build();

    userFeignClientSystem.createAuditLog(auditLog);

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
