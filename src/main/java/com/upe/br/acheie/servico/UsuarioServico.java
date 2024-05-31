package com.upe.br.acheie.servico;

import com.upe.br.acheie.repositorio.PostRepositorio;
import com.upe.br.acheie.repositorio.UsuarioRepositorio;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.upe.br.acheie.dominio.dto.UsuarioDto;
import com.upe.br.acheie.dominio.modelos.Usuario;
import com.upe.br.acheie.dominio.utils.AcheieException;
import com.upe.br.acheie.dominio.utils.MensagensErro;
import com.upe.br.acheie.dominio.utils.enums.Atualizacao;

@Service
@RequiredArgsConstructor
public class UsuarioServico {

  private final UsuarioRepositorio usuarioRepo;
  private final PostRepositorio postRepo;

  private static final Logger log = LogManager.getLogger(UsuarioServico.class);

  public UsuarioDto buscarUsuarioPorId(UUID idUsuario) {
    try {
      Optional<Usuario> usuario = usuarioRepo.findById(idUsuario);
      if (usuario.isEmpty()) {
        return null;
      }
      return new UsuarioDto(usuario.get());
    } catch (Exception e) {
      this.tratarErros(e);
    }
    return null;
  }

  public Atualizacao atualizarUsuario(UUID idUsuario, UsuarioDto usuarioDto) {
    try {
      Usuario usuario = usuarioRepo.getReferenceById(idUsuario);
      usuario.setNome(usuarioDto.nome());
      usuario.setSobrenome(usuarioDto.sobrenome());
      usuario.setTelefone(usuarioDto.telefone());
      usuario.setCurso(usuarioDto.curso());
      usuario.setPeriodo(usuarioDto.periodo());
      usuario.setFoto(usuarioDto.foto());

      this.usuarioRepo.save(usuario);

      return Atualizacao.ATUALIZACAO_COM_SUCESSO;
    } catch (Exception e) {
      this.tratarErros(e);
    }
    return Atualizacao.ATUALIZACAO_COM_FALHA;
  }

  public void tratarErros(Exception e) {
    if (e instanceof IllegalArgumentException) {
      log.error(MensagensErro.MSG_ID_NULO, e);
      throw new AcheieException(MensagensErro.MSG_ID_NULO, e);
    } else if (e instanceof NoSuchElementException) {
      log.error(MensagensErro.MSG_ERRO_OPTIONAL, e);
      throw new AcheieException(MensagensErro.MSG_ERRO_OPTIONAL, e);
    } else {
      log.error(e.getMessage(), e);
      throw new AcheieException(e.getMessage());
    }
  }

}
