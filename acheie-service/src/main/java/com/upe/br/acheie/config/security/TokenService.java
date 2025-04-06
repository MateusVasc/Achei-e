package com.upe.br.acheie.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.utils.AcheieException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  @Value("${app.security.secret-key}")
  private String SECRET;

  public String generateToken(User user) throws AcheieException {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET);

      return JWT.create()
          .withIssuer("acheie-api")
          .withSubject(user.getEmail())
          .withExpiresAt(generateExpirationDate())
          .sign(algorithm);

    } catch (JWTCreationException exception) {
      throw new AcheieException("Erro durante criação do token");
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET);

      return JWT.require(algorithm)
          .withIssuer("acheie-api")
          .build()
          .verify(token)
          .getSubject();

    } catch (JWTVerificationException exception) {
      return "";
    }
  }

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
