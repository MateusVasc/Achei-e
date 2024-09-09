package com.upe.br.acheie.service;

import com.upe.br.acheie.domain.model.User;
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

  private final UserRepository usuarioRepo;

  public UserDto buscarUsuarioPorId(UUID idUsuario) {
    User user = this.usuarioRepo.findById(idUsuario)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    return new UserDto(user);
  }

  public Atualizacao atualizarUsuario(UUID idUsuario, UserDto userDto) {
    User user = this.usuarioRepo.findById(idUsuario)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    user.setName(userDto.nome());
    user.setLastname(userDto.sobrenome());
    user.setTelephone(userDto.telefone());
    user.setCourse(userDto.course());
    user.setSemester(userDto.semester());
    user.setPhoto(userDto.foto());

    this.usuarioRepo.save(user);

    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }

  public String requisitarMudarSenha(String email) {
    email = email.replace(" ", "");
    this.usuarioRepo.getByEmail(email)
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));

    return "O link para redefinição de senha foi enviado para o seu e-mail.";
  }

  public Atualizacao atualizarSenha(LoginRequest login) {
    User user = this.usuarioRepo.getByEmail(login.email())
        .orElseThrow(() -> new AcheieException(ErrorMessage.MSG_USUARIO_NAO_ENCONTRADO));
    user.setPassword(new BCryptPasswordEncoder().encode(login.senha()));
    this.usuarioRepo.save(user);
    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }
}
