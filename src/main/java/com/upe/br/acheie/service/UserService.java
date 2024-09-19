package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.entities.User;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.domain.dto.UserDto;
import com.upe.br.acheie.domain.dto.request.LoginRequest;
import com.upe.br.acheie.domain.exceptions.AcheieException;
import com.upe.br.acheie.domain.exceptions.ErrorMessage;
import com.upe.br.acheie.domain.enums.Atualizacao;
import com.upe.br.acheie.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserDto searchUserById(UUID userId) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    return new UserDto(user);
  }

  public Atualizacao updateUser(UUID userId, UserDto userDto) {
    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    user.setName(userDto.name());
    user.setLastname(userDto.lastname());
    user.setTelephone(userDto.phone());
    user.setCourse(userDto.course());
    user.setSemester(userDto.semester());
    user.setPhoto(userDto.photo());

    this.userRepository.save(user);

    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }

  public String requestChangePassword(String email) {
    email = email.replace(" ", "");
    this.userRepository.getByEmail(email)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    return "O link para redefinição de password foi enviado para o seu e-mail.";
  }

  public Atualizacao updatePassword(LoginRequest login) {
    User user = this.userRepository.getByEmail(login.email())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));
    user.setPassword(new BCryptPasswordEncoder().encode(login.password()));
    this.userRepository.save(user);
    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }
}
