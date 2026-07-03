package com.example.gateway.util;

import com.example.gateway.dto.UsersRequestDto;
import com.example.gateway.dto.UserResponseDto;
import com.example.gateway.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserResponseDto convertToResponseDto(Users user) {
        if (user == null) {
            return null;
        }

        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .isActive(user.isActive())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .lastModifiedAt(user.getLastModifiedAt())
                .lastModifiedBy(user.getLastModifiedBy())
                .build();
    }

    public  Users convertToEntity (UsersRequestDto dto) {

        if (dto == null) {
            return null;
        }

        return Users.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .role(dto.getRole())
                .isActive(dto.isActive())
                .build();

    }

}
