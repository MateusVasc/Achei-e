package com.upe.br.auth.jobs;

import com.upe.br.auth.repositories.BlacklistedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TokenCleanupJob {

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void cleanupExpiredTokens() {
        blacklistedTokenRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
