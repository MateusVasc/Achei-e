package com.upe.br.acheie.controlador;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upe.br.acheie.dominio.dto.EmailDto;
import com.upe.br.acheie.dominio.dto.UsuarioDto;
import com.upe.br.acheie.dominio.dto.request.LoginRequest;
import com.upe.br.acheie.dominio.utils.MensagemUtil;
import com.upe.br.acheie.servico.UsuarioServico;

@RestController
@RequestMapping("/achei-e")
@RequiredArgsConstructor
public class UsuarioControle {

  private final UsuarioServico usuarioServico;

  @GetMapping("/usuario/{id}")
  public ResponseEntity<UsuarioDto> buscarUsuarioPorId(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(usuarioServico.buscarUsuarioPorId(id));
  }

  @PutMapping("/usuario")
  public ResponseEntity<MensagemUtil> atualizarUsuario(
      @RequestParam(value = "usuarioId") UUID idUsuario, @RequestBody UsuarioDto usuarioDto) {
    return ResponseEntity.status(HttpStatus.OK).body(new MensagemUtil(
        this.usuarioServico.atualizarUsuario(idUsuario, usuarioDto).getMensagem()));
  }

  @PutMapping("/usuario/nova-senha")
  public ResponseEntity<MensagemUtil> requisitarMudarSenha(@RequestBody EmailDto emailDto) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new MensagemUtil(this.usuarioServico.requisitarMudarSenha(emailDto.email())));
    }

    @PutMapping("usuario/redefinir-senha")
    public ResponseEntity<MensagemUtil> atualizarSenha (@RequestBody LoginRequest login){
      return ResponseEntity.status(HttpStatus.OK)
          .body(new MensagemUtil(this.usuarioServico.atualizarSenha(login).getMensagem()));
    }
  }
