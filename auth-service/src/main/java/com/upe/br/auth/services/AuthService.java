package com.upe.br.auth.services;

import com.upe.br.auth.domain.entities.BlacklistedToken;
import com.upe.br.auth.domain.entities.RefreshToken;
import com.upe.br.auth.domain.entities.Role;
import com.upe.br.auth.domain.entities.User;
import com.upe.br.auth.dtos.LoginRequest;
import com.upe.br.auth.dtos.LoginResponse;
import com.upe.br.auth.dtos.RefreshTokenRequest;
import com.upe.br.auth.dtos.RegisterRequest;
import com.upe.br.auth.exception.AuthException;
import com.upe.br.auth.exception.ExceptionMessages;
import com.upe.br.auth.repositories.BlacklistedTokenRepository;
import com.upe.br.auth.repositories.RefreshTokenRepository;
import com.upe.br.auth.repositories.RoleRepository;
import com.upe.br.auth.repositories.UserRepository;
import com.upe.br.auth.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final int LOCK_TIME_MINUTES = 30;
    private static final int MAX_REFRESH_TOKENS = 5;
    private static final int MAX_REFRESH_ATTEMPTS = 3;

    public void createUser(RegisterRequest request) {
        if (this.userRepository.findByEmail(request.email()).isPresent()) {
            throw new AuthException(ExceptionMessages.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(request.name());
        user.setLastname(request.lastname());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setMajor(request.major());
        user.setSemester(request.semester());
        user.setPhone(request.phone());
        user.setEnabled(true); // will be done by email request eventually
        user.setCreatedAt(LocalDateTime.now());

        Role userRole = this.roleRepository.findByName("USER")
                .orElseThrow(() ->
                        new AuthException(ExceptionMessages.ROLE_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR));

        user.setRoles(Set.of(userRole));

        this.userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = this.userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthException(ExceptionMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!user.isEnabled()) {
            throw new AuthException(ExceptionMessages.ACCOUNT_NOT_ENABLED, HttpStatus.FORBIDDEN);
        }

        if (user.getAccountLockedUntil() != null && user.getAccountLockedUntil().isAfter(LocalDateTime.now())) {
            throw new AuthException(ExceptionMessages.ACCOUNT_LOCKED, HttpStatus.FORBIDDEN);
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);

            user.setFailedLoginAttempts(0);
            user.setLastLoginAt(LocalDateTime.now());
            this.userRepository.save(user);

            String accessToken = this.jwtUtil.generateAccessToken(user);
            String refreshToken = this.jwtUtil.generateRefreshToken(user);

            RefreshToken refreshTokenToSave = new RefreshToken();
            refreshTokenToSave.setToken(refreshToken);
            refreshTokenToSave.setUser(user);
            refreshTokenToSave.setExpiresAt(jwtUtil.getExpirationDateFromToken(refreshToken));

            this.refreshTokenRepository.save(refreshTokenToSave);

            return new LoginResponse(user.getId(), accessToken, refreshToken);
        } catch (BadCredentialsException e) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if (user.getFailedLoginAttempts() >= MAX_LOGIN_ATTEMPTS) {
                user.setAccountLockedUntil(LocalDateTime.now().plusMinutes(LOCK_TIME_MINUTES));
                this.userRepository.save(user);
                throw new AuthException(ExceptionMessages.TOO_MANY_ATTEMPTS, HttpStatus.FORBIDDEN);
            }

            this.userRepository.save(user);
            throw new AuthException(ExceptionMessages.INVALID_CREDENTIALS, e, HttpStatus.FORBIDDEN);
        }
    }

    public LoginResponse refreshToken(RefreshTokenRequest request) {
        String oldAccessToken = request.accessToken();
        String email = this.jwtUtil.validateToken(request.refreshToken());

        if (email == null) {
            throw new AuthException(ExceptionMessages.INVALID_TOKEN, HttpStatus.FORBIDDEN);
        }

        if (oldAccessToken == null || oldAccessToken.isEmpty()) {
            throw new AuthException(ExceptionMessages.INVALID_TOKEN, HttpStatus.FORBIDDEN);
        }

        RefreshToken refreshToken = this.refreshTokenRepository.findByTokenAndIsRevokedFalse(request.refreshToken())
                .orElseThrow(() -> new AuthException(ExceptionMessages.TOKEN_WAS_REVOKED, HttpStatus.FORBIDDEN));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshToken.setIsRevoked(true);
            throw new AuthException(ExceptionMessages.TOKEN_EXPIRED, HttpStatus.FORBIDDEN);
        }

        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(ExceptionMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (!user.isEnabled()) {
            throw new AuthException(ExceptionMessages.ACCOUNT_LOCKED, HttpStatus.FORBIDDEN);
        }

        if (user.getAccountLockedUntil() != null && user.getAccountLockedUntil().isAfter(LocalDateTime.now())) {
            throw new AuthException(ExceptionMessages.ACCOUNT_LOCKED, HttpStatus.FORBIDDEN);
        }

        if (!this.blacklistedTokenRepository.existsByToken(oldAccessToken)) {
            BlacklistedToken accessTokenToRevoke = new BlacklistedToken();
            accessTokenToRevoke.setToken(oldAccessToken);
            accessTokenToRevoke.setExpiresAt(jwtUtil.getExpirationDateFromToken(oldAccessToken));
            accessTokenToRevoke.setRevokedAt(LocalDateTime.now());
            accessTokenToRevoke.setUser(user);
            this.blacklistedTokenRepository.save(accessTokenToRevoke);
        }

        refreshToken.setIsRevoked(true);
        refreshToken.setLastUsedAt(LocalDateTime.now());
        this.refreshTokenRepository.save(refreshToken);

        long activeRefreshTokens = this.refreshTokenRepository.countByUserAndIsRevokedFalse(user);

        if (activeRefreshTokens >= MAX_REFRESH_TOKENS) {
            this.refreshTokenRepository.findTopByUserAndIsRevokedFalseOrderByCreatedAtAsc(user)
                    .ifPresent(oldRefreshToken -> {
                        oldRefreshToken.setIsRevoked(true);
                        this.refreshTokenRepository.save(oldRefreshToken);
                    });
        }

        String newAccessToken = this.jwtUtil.generateAccessToken(user);
        String newRefreshToken = this.jwtUtil.generateRefreshToken(user);

        RefreshToken refreshTokenToSave = new RefreshToken();
        refreshTokenToSave.setToken(newRefreshToken);
        refreshTokenToSave.setUser(user);
        refreshTokenToSave.setExpiresAt(jwtUtil.getExpirationDateFromToken(newRefreshToken));

        this.refreshTokenRepository.save(refreshTokenToSave);

        return new LoginResponse(user.getId(), newAccessToken, newRefreshToken);
    }

    public void logout(RefreshTokenRequest request) {
        String email = this.jwtUtil.validateToken(request.refreshToken());

        if (email == null) {
            throw new AuthException(ExceptionMessages.INVALID_TOKEN, HttpStatus.FORBIDDEN);
        }

        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(ExceptionMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        RefreshToken token = this.refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new AuthException(ExceptionMessages.INVALID_TOKEN, HttpStatus.FORBIDDEN));

        if (!token.getUser().getId().equals(user.getId())) {
            throw new AuthException(ExceptionMessages.TOKEN_NOT_BELONG_TO_USER, HttpStatus.FORBIDDEN);
        }

        if (token.getIsRevoked()) {
            throw new AuthException(ExceptionMessages.TOKEN_WAS_REVOKED, HttpStatus.FORBIDDEN);
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            token.setIsRevoked(true);
            this.refreshTokenRepository.save(token);
            throw new AuthException(ExceptionMessages.TOKEN_EXPIRED, HttpStatus.FORBIDDEN);
        }

        token.setIsRevoked(true);
        token.setLastUsedAt(LocalDateTime.now());
        this.refreshTokenRepository.save(token);

        BlacklistedToken blacklistedToken = new BlacklistedToken();
        blacklistedToken.setToken(request.accessToken());
        blacklistedToken.setExpiresAt(jwtUtil.getExpirationDateFromToken(request.accessToken()));
        blacklistedToken.setUser(user);
        this.blacklistedTokenRepository.save(blacklistedToken);

        this.cleanupOldTokens(user);
    }

    private void cleanupOldTokens(User user) {
        this.refreshTokenRepository.findByUserAndExpiresAtBeforeAndIsRevokedFalse(user, LocalDateTime.now())
                .forEach(token -> {
                    token.setIsRevoked(true);
                    this.refreshTokenRepository.save(token);
                });

        this.refreshTokenRepository.findByUserAndIsRevokedFalseOrderByCreatedAtDesc(user)
                .stream()
                .skip(5)
                .forEach(token -> {
                    token.setIsRevoked(true);
                    this.refreshTokenRepository.save(token);
                });
    }
}
