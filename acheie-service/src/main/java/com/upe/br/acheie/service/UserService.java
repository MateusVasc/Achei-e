package com.upe.br.acheie.service;

import java.util.UUID;

import com.upe.br.acheie.domain.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dtos.UserDTO;
import com.upe.br.acheie.dtos.request.LoginRequest;
import com.upe.br.acheie.utils.AcheieException;
import com.upe.br.acheie.utils.ErrorMessages;
import com.upe.br.acheie.domain.enums.Update;
import com.upe.br.acheie.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserDTO searchUserById(UUID userId) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_USER_NOT_FOUND));

    return new UserDTO(user);
  }

  public Update updateUser(UUID userId, UserDTO userDTO) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_USER_NOT_FOUND));

    user.setName(userDTO.name());
    user.setLastname(userDTO.lastname());
    user.setPhone(userDTO.phone());
    user.setMajor(userDTO.major());
    user.setSemester(userDTO.semester());
    user.setImage(userDTO.image());

    this.userRepository.save(user);

    return Update.UPDATED_SUCCESSFULLY;
  }

  public String requestPasswordChange(String email) {
    email = email.replace(" ", "");
    this.userRepository.getByEmail(email)
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_USER_NOT_FOUND));

    return "O link para redefinição de password foi enviado para o seu e-mail.";
  }

  public Update updatePassword(LoginRequest login) {
    User user = this.userRepository.getByEmail(login.email())
        .orElseThrow(() -> new AcheieException(ErrorMessages.MSG_USER_NOT_FOUND));
    user.setPassword(new BCryptPasswordEncoder().encode(login.password()));
    this.userRepository.save(user);
    return Update.UPDATED_SUCCESSFULLY;
  }
}
