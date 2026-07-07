package com.example.gateway.usercontext;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Long userId = UserContext.getUserId();

        if (userId == null) {
            return Optional.of(1L);
        }

        return Optional.of(userId);
    }
}