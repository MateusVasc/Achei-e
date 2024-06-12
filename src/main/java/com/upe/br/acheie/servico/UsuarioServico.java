package com.upe.br.acheie.servico;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.dto.UsuarioDto;
import com.upe.br.acheie.dominio.dto.request.LoginRequest;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.utils.AcheieException;
import com.upe.br.acheie.dominio.utils.MensagensErro;
import com.upe.br.acheie.dominio.utils.enums.Atualizacao;
import com.upe.br.acheie.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServico {

  private final UsuarioRepositorio usuarioRepo;

  public UsuarioDto buscarUsuarioPorId(UUID idUsuario) {
    Usuario usuario = this.usuarioRepo.findById(idUsuario)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_USUARIO_NAO_ENCONTRADO));

    return new UsuarioDto(usuario);
  }

  public Atualizacao atualizarUsuario(UUID idUsuario, UsuarioDto usuarioDto) {
    Usuario usuario = this.usuarioRepo.findById(idUsuario)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_USUARIO_NAO_ENCONTRADO));

    usuario.setNome(usuarioDto.nome());
    usuario.setSobrenome(usuarioDto.sobrenome());
    usuario.setTelefone(usuarioDto.telefone());
    usuario.setCurso(usuarioDto.curso());
    usuario.setPeriodo(usuarioDto.periodo());
    usuario.setFoto(usuarioDto.foto());

    this.usuarioRepo.save(usuario);

    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }

  public String requisitarMudarSenha(String email) {
    email = email.replace(" ", "");
    this.usuarioRepo.getByEmail(email)
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_USUARIO_NAO_ENCONTRADO));

    return "O link para redefinição de senha foi enviado para o seu e-mail.";
  }

  public Atualizacao atualizarSenha(LoginRequest login) {
    Usuario usuario = this.usuarioRepo.getByEmail(login.email())
        .orElseThrow(() -> new AcheieException(MensagensErro.MSG_USUARIO_NAO_ENCONTRADO));
    usuario.setSenha(new BCryptPasswordEncoder().encode(login.senha()));
    this.usuarioRepo.save(usuario);
    return Atualizacao.ATUALIZACAO_COM_SUCESSO;
  }
}
