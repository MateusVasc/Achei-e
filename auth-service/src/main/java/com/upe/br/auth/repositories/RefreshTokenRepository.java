package com.upe.br.auth.repositories;

import com.upe.br.auth.domain.entities.RefreshToken;
import com.upe.br.auth.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByTokenAndIsRevokedFalse(String token);

    long countByUserAndIsRevokedFalse(User user);

    Optional<RefreshToken> findTopByUserAndIsRevokedFalseOrderByCreatedAtAsc(User user);

    List<RefreshToken> findByUserAndExpiresAtBeforeAndIsRevokedFalse(User user, LocalDateTime dateTime);

    List<RefreshToken> findByUserAndIsRevokedFalseOrderByCreatedAtDesc(User user);
}
