package com.upe.br.acheie.config;

import com.upe.br.acheie.repositorio.UsuarioRepositorio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  private final TokenServico tokenServico;
  private final UsuarioRepositorio usuarioRepositorio;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    var token = this.recuperarToken(request);

    if (token != null) {
      var subject = tokenServico.validarToken(token);
      UserDetails usuario = this.usuarioRepositorio.findByEmail(subject);

      var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
          usuario.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String recuperarToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");

    if (authHeader == null) {
      return null;
    }

    return authHeader.replace("Bearer ", "");
  }
}
