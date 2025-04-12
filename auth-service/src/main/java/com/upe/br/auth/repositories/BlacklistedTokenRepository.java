package com.upe.br.auth.repositories;

import com.upe.br.auth.domain.entities.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, UUID> {

    boolean existsByToken(String token);

    void deleteByExpiresAtBefore(LocalDateTime date);
}
