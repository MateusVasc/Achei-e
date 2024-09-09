package com.upe.br.acheie.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.upe.br.acheie.domain.model.User;
import com.upe.br.acheie.domain.exceptions.AcheieException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private static final String SECRET_KEY = System.getenv("SECRET_KEY");

  public String gerarToken(User user) throws AcheieException {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

      return JWT.create()
          .withIssuer("acheie-api")
          .withSubject(user.getEmail())
          .withExpiresAt(gerarDataDeExpiracao())
          .sign(algorithm);

    } catch (JWTCreationException exception) {
      throw new AcheieException("Erro durante criação do token");
    }
  }

  public String validarToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

      return JWT.require(algorithm)
          .withIssuer("acheie-api")
          .build()
          .verify(token)
          .getSubject();

    } catch (JWTVerificationException exception) {
      return "";
    }
  }

  private Instant gerarDataDeExpiracao() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
