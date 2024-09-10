package com.upe.br.acheie.service;

import com.upe.br.acheie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails user = this.userRepository.findByEmail(username);

    if (user == null) {
      throw new UsernameNotFoundException("Usuario não encontrado para o email: " + username);
    }

    return user;
  }
}
