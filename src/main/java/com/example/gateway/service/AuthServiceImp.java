package com.example.gateway.service;

import com.example.gateway.dto.UsersRequestDto;
import com.example.gateway.entity.Users;
import com.example.gateway.repository.UserRepository;
import com.example.gateway.security.JwtUtils;
import com.example.gateway.util.UserMapper;
import com.example.gateway.usercontext.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    @Override
    public void registerUser(UsersRequestDto requestDto) {

        if (requestDto == null) {
            throw new IllegalArgumentException("Registration request data cannot be null.");
        }

        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        if (!PASSWORD_PATTERN.matcher(requestDto.getPassword()).matches()) {
            throw new IllegalArgumentException("Password invalid. Requires min 8 chars, 1 uppercase, 1 digit, 1 special char.");
        }

        UserContext.setUserId(1L);

        Users user = userMapper.convertToEntity(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);

        Users savedUser = userRepository.save(user);

        UserContext.setUserId(savedUser.getId());

        savedUser.setCreatedBy(savedUser.getId());
        savedUser.setLastModifiedBy(savedUser.getId());

        userRepository.save(savedUser);

        UserContext.clear();
    }

    @Override
    public ResponseCookie authenticateAndBuildCookie(String username, String password) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));

        if (!user.isActive()) {
            throw new IllegalArgumentException("Account is currently deactivated.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        UserContext.setUserId(user.getId());

        try {
            user.setLastLogin(LocalDateTime.now(ZoneId.of("UTC")));
            user.setLastModifiedBy(user.getId());

            userRepository.save(user);
        } finally {
            UserContext.clear();
        }

        return jwtUtils.generateJwtCookie(user.getUsername(), user.getRole().name(), user.getId());
    }

    @Override
    public boolean validateTokenString(String token) {
        return jwtUtils.validateToken(token);
    }

    @Override
    public void updateUser(Long id, UsersRequestDto requestDto) {

        if (requestDto == null) {
            throw new IllegalArgumentException("Update request data cannot be null.");
        }

        Users existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        existingUser.setUsername(requestDto.getUsername());
        existingUser.setEmail(requestDto.getEmail());
        existingUser.setActive(requestDto.isActive());

        if (requestDto.getRole() != null) {
            existingUser.setRole(requestDto.getRole());
        }

        if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }

        Long modifierId = (UserContext.getUserId() != null) ? UserContext.getUserId() : id;
        existingUser.setLastModifiedBy(modifierId);

        userRepository.save(existingUser);
    }
}
