package com.example.gateway.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;


    @CreatedDate
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT '2026-06-23 19:54:30'")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", nullable = false, columnDefinition = "BIGINT DEFAULT 1")
    private Long createdBy;

    @LastModifiedDate

    @Column(name = "last_modified_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT '2026-06-23 19:54:30'")
    private LocalDateTime lastModifiedAt;

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false, columnDefinition = "BIGINT DEFAULT 1")
    private Long lastModifiedBy;


    @PrePersist
    protected void onCreate() {
        LocalDateTime nowUtc = LocalDateTime.now(ZoneId.of("UTC"));
        this.createdAt = nowUtc;
        this.lastModifiedAt = nowUtc;

        long currentActor = 1L;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Long cachedId = (Long) attributes.getAttribute("AUDIT_USER_ID", ServletRequestAttributes.SCOPE_REQUEST);
            if (cachedId != null) {
                currentActor = cachedId;
            }
        }

        this.createdBy = currentActor;
        this.lastModifiedBy = currentActor;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedAt = LocalDateTime.now(ZoneId.of("UTC"));

        long currentActor = 1L;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Long cachedId = (Long) attributes.getAttribute("AUDIT_USER_ID", ServletRequestAttributes.SCOPE_REQUEST);
            if (cachedId != null) {
                currentActor = cachedId;
            }
        }

        this.lastModifiedBy = currentActor;
    }



}
