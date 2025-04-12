package com.upe.br.auth.services;

import com.upe.br.auth.domain.entities.Role;
import com.upe.br.auth.domain.entities.User;
import com.upe.br.auth.domain.enums.Major;
import com.upe.br.auth.domain.enums.Semester;
import com.upe.br.auth.dtos.RegisterRequest;
import com.upe.br.auth.exception.AuthException;
import com.upe.br.auth.repositories.RoleRepository;
import com.upe.br.auth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    // User Registration
    @Nested
    @DisplayName("User Registration Testes")
    class RegistrationTests{
        private static final UUID ID = UUID.randomUUID();
        private static final String NAME = "Mateus";
        private static final String LASTNAME = "Vasconcelos";
        private static final String EMAIL = "mateus.mateus@gmail.com";
        private static final String PASSWORD = "Str0ngP4ss!@";
        private static final Major MAJOR = Major.SOFTWARE_ENGINEERING;
        private static final Semester SEMESTER = Semester.SEVENTH;
        private static final String PHONE = "+5511999999999";

        private static final String USER_ROLE = "USER";

        private static RegisterRequest request;

        private static User user;
        private static Role role;

        @BeforeAll
        static void setUp() {
            request = new RegisterRequest(
                    NAME,
                    LASTNAME,
                    EMAIL,
                    PASSWORD,
                    MAJOR,
                    SEMESTER,
                    PHONE,
                    null
            );

            user = new User();
            user.setId(ID);

            role = new Role();
            role.setName(USER_ROLE);
        }

        @Test
        @DisplayName("Should create new user when all data is valid")
        void shouldCreateNewUser_WhenAllDataIsValid() {
            when(userRepository.findByEmail(request.email()))
                    .thenReturn(Optional.empty());

            when(roleRepository.findByName(USER_ROLE))
                    .thenReturn(Optional.of(role));

            when(passwordEncoder.encode(PASSWORD))
                    .thenReturn("encodedPassword");

            authService.createUser(request);

            verify(userRepository, times(1)).findByEmail(any(String.class));
            verify(roleRepository, times(1)).findByName(any(String.class));
            verify(passwordEncoder, times(1)).encode(PASSWORD);
            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("Should throw AuthException when trying to create user with existing email")
        void shouldThrowAuthException_WhenCreatingUserWithExistingEmail() {
            when(userRepository.findByEmail(request.email()))
                    .thenReturn(Optional.of(user));

            AuthException exception = assertThrows(AuthException.class, () -> {
                authService.createUser(request);
            });

            verify(userRepository, times(1)).findByEmail(any(String.class));
            assertEquals("Email already taken", exception.getMessage());
            assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        }

        @Test
        @DisplayName("Should throw AuthException when role USER is not found")
        void shouldThrowAuthException_WhenUserRoleNotFound() {
            when(userRepository.findByEmail(request.email()))
                    .thenReturn(Optional.empty());

            when(roleRepository.findByName(USER_ROLE))
                    .thenReturn(Optional.empty());

            AuthException exception = assertThrows(AuthException.class, () -> {
                authService.createUser(request);
            });

            verify(userRepository, times(1)).findByEmail(any(String.class));
            verify(roleRepository, times(1)).findByName(any(String.class));
            assertEquals("Role not found", exception.getMessage());
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        }
    }

    // Login
    @Nested
    @DisplayName("User Login Tests")
    class LoginTests {
        @BeforeAll
        static void setUp() {}

        @Test
        @DisplayName("Should return access and refresh tokens when login credentials are valid")
        void shouldReturnAccessAndRefreshTokens_WhenLoginCredentialsAreValid() {}

        @Test
        @DisplayName("Should throw AuthException when user email does not exist")
        void shouldThrowAuthException_WhenUserEmailDoesNotExist() {}

        @Test
        @DisplayName("Should throw AuthException when password is incorrect")
        void shouldThrowAuthException_WhenPasswordIsIncorrect() {}

        @Test
        @DisplayName("Should throw AuthException when account is not enabled")
        void shouldThrowAuthException_WhenAccountIsNotEnabled() {}

        @Test
        @DisplayName("Should throw AuthException when account is locked")
        void shouldThrowAuthException_WhenAccountIsLocked() {}

        @Test
        @DisplayName("Should increment failed login attempts when password is incorrect")
        void shouldIncrementFailedLoginAttempts_WhenPasswordIsIncorrect() {}

        @Test
        @DisplayName("Should lock account when maximum login attempts is reached")
        void shouldLockAccount_WhenMaximumLoginAttemptsReached() {}

        @Test
        @DisplayName("Should reset failed login attempts when login is successful")
        void shouldResetFailedLoginAttempts_WhenLoginIsSuccessful() {}
    }

    // Refresh Token
    @Nested
    @DisplayName("Refreah Login Tests")
    class RefreshTests {

        @Test
        @DisplayName("Should return new access and refresh tokens when refresh token is valid")
        void shouldReturnNewTokens_WhenRefreshTokenIsValid() {}

        @Test
        @DisplayName("Should throw AuthException when refresh token is invalid")
        void shouldThrowAuthException_WhenRefreshTokenIsInvalid() {}

        @Test
        @DisplayName("Should throw AuthException when refresh token is expired")
        void shouldThrowAuthException_WhenRefreshTokenIsExpired() {}

        @Test
        @DisplayName("Should throw AuthException when refresh token is revoked")
        void shouldThrowAuthException_WhenRefreshTokenIsRevoked() {}

        @Test
        @DisplayName("Should revoke old refresh token when generating new one")
        void shouldRevokeOldRefreshToken_WhenGeneratingNewOne() {}

        @Test
        @DisplayName("Should throw AuthException when account is locked during refresh")
        void shouldThrowAuthException_WhenAccountIsLockedDuringRefresh() {}

        @Test
        @DisplayName("Should throw AuthException when account is not enabled during refresh")
        void shouldThrowAuthException_WhenAccountIsNotEnabledDuringRefresh() {}

        @Test
        @DisplayName("Should remove oldest refresh token when maximum limit is reached")
        void shouldRemoveOldestRefreshToken_WhenMaximumLimitIsReached() {}
    }

    // Logout
    @Nested
    @DisplayName("User Logout Tests")
    class LogoutTests {

        @Test
        @DisplayName("Should revoke refresh token and blacklist access token when logout is successful")
        void shouldRevokeRefreshTokenAndBlacklistAccessToken_WhenLogoutIsSuccessful() {}

        @Test
        @DisplayName("Should throw AuthException when refresh token is invalid during logout")
        void shouldThrowAuthException_WhenRefreshTokenIsInvalidDuringLogout() {}

        @Test
        @DisplayName("Should throw AuthException when refresh token is expired during logout")
        void shouldThrowAuthException_WhenRefreshTokenIsExpiredDuringLogout() {}

        @Test
        @DisplayName("Should throw AuthException when refresh token is revoked during logout")
        void shouldThrowAuthException_WhenRefreshTokenIsRevokedDuringLogout() {}

        @Test
        @DisplayName("Should throw AuthException when refresh token does not belong to user")
        void shouldThrowAuthException_WhenRefreshTokenDoesNotBelongToUser() {}
    }
}
