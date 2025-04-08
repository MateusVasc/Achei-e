package com.upe.br.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.upe.br.auth.domain.entities.Permission;
import com.upe.br.auth.domain.entities.Role;
import com.upe.br.auth.domain.entities.User;
import com.upe.br.auth.exception.AuthException;
import com.upe.br.auth.exception.ExceptionMessages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUtil {

    @Value("${app.security.jwt.secret}")
    private String secret;

    public String generateAccessToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("auth-upe-api")
                    .withSubject(user.getEmail())
                    .withClaim("roles", collectRoles(user))
                    .withClaim("permissions", collectPermissions(user))
                    .withExpiresAt(generateExpirationDateForAccessToken())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new AuthException(ExceptionMessages.FAILED_TO_CREATE_TOKEN, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generateRefreshToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("auth-upe-api")
                    .withSubject(user.getEmail())
                    .withClaim("roles", collectRoles(user))
                    .withClaim("permissions", collectPermissions(user))
                    .withExpiresAt(generateExpirationDateForRefreshToken())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new AuthException(ExceptionMessages.FAILED_TO_CREATE_TOKEN, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("auth-upe-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException e) {
            return null;
        }
    }

    private Instant generateExpirationDateForAccessToken() {
        return LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant generateExpirationDateForRefreshToken() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00"));
    }

    private List<String> collectRoles(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    private List<String> collectPermissions(User user) {
        return user.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .collect(Collectors.toList());
    }
}
