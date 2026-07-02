package com.example.gateway.service;

import com.example.gateway.entity.Users;
import com.example.gateway.repository.UserRepository;
import com.example.gateway.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");


    public void registerUser(Users user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
            throw new IllegalArgumentException("Password invalid. Requires min 8 chars, 1 uppercase, 1 digit, 1 special char.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);
    }

    public ResponseCookie authenticateAndBuildCookie(String username, String password) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));

        if (!user.isActive()) {
            throw new IllegalArgumentException("Account is currently deactivated.");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        user.setLastLogin(LocalDateTime.now(ZoneId.of("UTC")));
        userRepository.save(user);

        return jwtUtils.generateJwtCookie(user.getUsername(), user.getRole().name(),user.getId());
    }

    public boolean validateTokenString(String token) {
        return jwtUtils.validateToken(token);
    }

}
