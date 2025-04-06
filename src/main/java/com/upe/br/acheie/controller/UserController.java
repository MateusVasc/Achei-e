package com.upe.br.acheie.controller;

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

import com.upe.br.acheie.dtos.EmailDto;
import com.upe.br.acheie.dtos.UsuarioDto;
import com.upe.br.acheie.dtos.request.LoginRequest;
import com.upe.br.acheie.utils.MensagemUtil;
import com.upe.br.acheie.service.UsuarioServico;

@RestController
@RequestMapping("/achei-e")
@RequiredArgsConstructor
public class UserController {

  private final UsuarioServico usuarioServico;

  @GetMapping("/usuario/{id}")
  public ResponseEntity<UsuarioDto> searchUserById(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(usuarioServico.buscarUsuarioPorId(id));
  }

  @PutMapping("/usuario")
  public ResponseEntity<MensagemUtil> updateUser(
      @RequestParam(value = "usuarioId") UUID idUsuario, @RequestBody UsuarioDto usuarioDto) {
    return ResponseEntity.status(HttpStatus.OK).body(new MensagemUtil(
        this.usuarioServico.atualizarUsuario(idUsuario, usuarioDto).getMessage()));
  }

  @PutMapping("/usuario/nova-senha")
  public ResponseEntity<MensagemUtil> requestPasswordChange(@RequestBody EmailDto emailDto) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new MensagemUtil(this.usuarioServico.requisitarMudarSenha(emailDto.email())));
    }

    @PutMapping("usuario/redefinir-senha")
    public ResponseEntity<MensagemUtil> updatePassword(@RequestBody LoginRequest login){
      return ResponseEntity.status(HttpStatus.OK)
          .body(new MensagemUtil(this.usuarioServico.atualizarSenha(login).getMessage()));
    }
}
