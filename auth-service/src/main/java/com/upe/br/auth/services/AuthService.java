package com.upe.br.auth.services;

import com.upe.br.auth.domain.entities.Role;
import com.upe.br.auth.domain.entities.User;
import com.upe.br.auth.dtos.LoginRequest;
import com.upe.br.auth.dtos.LoginResponse;
import com.upe.br.auth.dtos.RegisterRequest;
import com.upe.br.auth.exception.AuthException;
import com.upe.br.auth.exception.ExceptionMessages;
import com.upe.br.auth.repositories.RoleRepository;
import com.upe.br.auth.repositories.UserRepository;
import com.upe.br.auth.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public void createUser(RegisterRequest request) {
        if (this.userRepository.findByEmail(request.email()).isPresent()) {
            throw new AuthException(ExceptionMessages.USER_ALREADY_EXISTIS);
        }

        User user = new User();
        user.setName(request.name());
        user.setLastname(request.lastname());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setMajor(request.major());
        user.setSemester(request.semester());
        user.setPhone(request.phone());

        Role userRole = this.roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Set.of(userRole));

        this.userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = this.userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        String accessToken = this.jwtUtil.generateAccessToken((User) auth.getPrincipal());

        return new LoginResponse(user.getId(), accessToken);
    }
}
