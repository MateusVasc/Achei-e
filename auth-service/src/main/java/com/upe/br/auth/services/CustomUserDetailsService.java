package com.upe.br.auth.services;

import com.upe.br.auth.exception.AuthException;
import com.upe.br.auth.exception.ExceptionMessages;
import com.upe.br.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws AuthException {
        return this.userRepository.findByEmail(email)
                .map(user -> new User(
                        user.getEmail(),
                        user.getPassword(),
                        user.getAuthorities()
                ))
                .orElseThrow(() -> new AuthException(ExceptionMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
