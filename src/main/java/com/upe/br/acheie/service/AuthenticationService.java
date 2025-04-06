package com.upe.br.acheie.service;

import com.upe.br.acheie.config.security.TokenService;
import com.upe.br.acheie.domain.models.User;
import com.upe.br.acheie.dtos.request.RegisterRequest;
import com.upe.br.acheie.dtos.response.RegisterResponse;
import com.upe.br.acheie.dtos.request.LoginRequest;
import com.upe.br.acheie.dtos.response.DeleteAccountResponse;
import com.upe.br.acheie.dtos.response.LoginResponse;
import com.upe.br.acheie.utils.ErrorMessages;
import com.upe.br.acheie.repository.UserRepository;
import com.upe.br.acheie.utils.AcheieException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;
  private final UserRepository userRepository;

  public LoginResponse loginUser(LoginRequest request) throws AcheieException {
    var userData = new UsernamePasswordAuthenticationToken(request.email(), request.password());
    var auth = this.authenticationManager.authenticate(userData);

    var token = tokenService.generateToken((User) auth.getPrincipal());
    User user = (User) this.userRepository.findByEmail(request.email());
    return new LoginResponse(user.getId(), token);
  }

  public RegisterResponse registerUser(RegisterRequest request) throws AcheieException {
    if (!validateInfo(request)) {
      throw new AcheieException(ErrorMessages.MSG_INVALID_REGISTRATION_INFO);
    }

    if (this.userRepository.findByEmail(request.email()) != null) {
      throw new AcheieException(ErrorMessages.MSG_EMAIL_REGISTERED);
    }

    User newUser = new User(request);
    newUser.setPassword(new BCryptPasswordEncoder().encode(request.password()));
    newUser.setCreatedAt(LocalDate.now());
    this.userRepository.save(newUser);

    return new RegisterResponse(newUser.getEmail(), newUser.getCreatedAt());
  }

  public DeleteAccountResponse excluirUsuarioPorEmail(String email) throws AcheieException {
    if (!validateEmail(email)) {
      throw new AcheieException(ErrorMessages.MSG_INVALID_EMAIL);
    }

    if (this.userRepository.findByEmail(email) == null) {
      throw new AcheieException(ErrorMessages.MSG_EMAIL_REGISTERED);
    }

    User userToDelete = (User) this.userRepository.findByEmail(email);
    this.userRepository.deleteById(userToDelete.getId());
    userToDelete.setDeletedAt(LocalDate.now());

    return new DeleteAccountResponse(userToDelete.getEmail(),
        userToDelete.getCreatedAt(), userToDelete.getDeletedAt());
  }

  private boolean validateInfo(RegisterRequest request) {
    return validateName(request.name()) &&
        validateLastname(request.lastname()) &&
        validateEmail(request.email()) &&
        validatePassword(request.password()) &&
        validatePhone(request.phone());
  }

  private boolean validateName(String name) {
    return name != null && !name.trim().isEmpty() && name.length() > 4;
  }

  private boolean validateLastname(String lastname) {
    return lastname != null && !lastname.trim().isEmpty() && lastname.length() > 4;
  }

  private boolean validateEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return false;
    }

    String emailRegex = "^[^@]{5,}@upe\\.br$";
    Pattern pattern = Pattern.compile(emailRegex);
    Matcher matcher = pattern.matcher(email);

    return matcher.matches();
  }

  private boolean validatePassword(String password) {
    if (password == null || password.trim().isEmpty()) {
      return false;
    }

    String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    Pattern pattern = Pattern.compile(passwordRegex);
    Matcher matcher = pattern.matcher(password);

    return matcher.matches();
  }

  private boolean validatePhone(String phone) {
    if (phone == null || phone.trim().isEmpty()) {
      return false;
    }

    String phoneRegex = "^\\d{11}$";
    Pattern pattern = Pattern.compile(phoneRegex);
    Matcher matcher = pattern.matcher(phone);

    return matcher.matches();
  }
}
