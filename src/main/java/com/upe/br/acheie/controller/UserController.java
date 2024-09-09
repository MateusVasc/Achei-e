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

import com.upe.br.acheie.domain.dto.EmailDto;
import com.upe.br.acheie.domain.dto.UserDto;
import com.upe.br.acheie.domain.dto.request.LoginRequest;
import com.upe.br.acheie.domain.exceptions.UtilMessage;
import com.upe.br.acheie.service.UserService;

@RestController
@RequestMapping("/achei-e")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/usuario/{id}")
  public ResponseEntity<UserDto> buscarUsuarioPorId(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.buscarUsuarioPorId(id));
  }

  @PutMapping("/usuario")
  public ResponseEntity<UtilMessage> atualizarUsuario(
      @RequestParam(value = "usuarioId") UUID idUsuario, @RequestBody UserDto userDto) {
    return ResponseEntity.status(HttpStatus.OK).body(new UtilMessage(
        this.userService.atualizarUsuario(idUsuario, userDto).getMensagem()));
  }

  @PutMapping("/usuario/nova-senha")
  public ResponseEntity<UtilMessage> requisitarMudarSenha(@RequestBody EmailDto emailDto) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new UtilMessage(this.userService.requisitarMudarSenha(emailDto.email())));
    }

    @PutMapping("usuario/redefinir-senha")
    public ResponseEntity<UtilMessage> atualizarSenha (@RequestBody LoginRequest login){
      return ResponseEntity.status(HttpStatus.OK)
          .body(new UtilMessage(this.userService.atualizarSenha(login).getMensagem()));
    }
  }
