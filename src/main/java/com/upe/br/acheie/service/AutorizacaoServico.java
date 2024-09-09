package com.upe.br.acheie.service;

import com.upe.br.acheie.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutorizacaoServico implements UserDetailsService {

  private final UsuarioRepositorio usuarioRepositorio;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails usuario = this.usuarioRepositorio.findByEmail(username);

    if (usuario == null) {
      throw new UsernameNotFoundException("Usuario não encontrado para o email: " + username);
    }

    return usuario;
  }
}
