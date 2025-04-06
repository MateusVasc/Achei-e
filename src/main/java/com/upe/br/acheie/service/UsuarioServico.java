package com.upe.br.acheie.service;

import java.util.UUID;

import com.upe.br.acheie.domain.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dtos.UsuarioDto;
import com.upe.br.acheie.dtos.request.LoginRequest;
import com.upe.br.acheie.utils.AcheieException;
import com.upe.br.acheie.utils.MensagensErro;
import com.upe.br.acheie.domain.enums.Update;
import com.upe.br.acheie.repository.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServico {

  private final UsuarioRepositorio usuarioRepo;

  public UsuarioDto buscarUsuarioPorId(UUID idUsuario) {
    User user = this.usuarioRepo.findById(idUsuario)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_USUARIO_NAO_ENCONTRADO));

    return new UsuarioDto(user);
  }

  public Update atualizarUsuario(UUID idUsuario, UsuarioDto usuarioDto) {
    User user = this.usuarioRepo.findById(idUsuario)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_USUARIO_NAO_ENCONTRADO));

    user.setName(usuarioDto.nome());
    user.setLastname(usuarioDto.sobrenome());
    user.setPhone(usuarioDto.telefone());
    user.setMajor(usuarioDto.major());
    user.setSemester(usuarioDto.semester());
    user.setImage(usuarioDto.foto());

    this.usuarioRepo.save(user);

    return Update.UPDATED_SUCESSFULLY;
  }

  public String requisitarMudarSenha(String email) {
    email = email.replace(" ", "");
    this.usuarioRepo.getByEmail(email)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_USUARIO_NAO_ENCONTRADO));

    return "O link para redefinição de senha foi enviado para o seu e-mail.";
  }

  public Update atualizarSenha(LoginRequest login) {
    User user = this.usuarioRepo.getByEmail(login.email())
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_USUARIO_NAO_ENCONTRADO));
    user.setPassword(new BCryptPasswordEncoder().encode(login.senha()));
    this.usuarioRepo.save(user);
    return Update.UPDATED_SUCESSFULLY;
  }
}
