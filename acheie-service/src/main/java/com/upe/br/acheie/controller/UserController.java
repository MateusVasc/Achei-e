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

import com.upe.br.acheie.dtos.EmailDTO;
import com.upe.br.acheie.dtos.UserDTO;
import com.upe.br.acheie.dtos.request.LoginRequest;
import com.upe.br.acheie.utils.MessageUtil;
import com.upe.br.acheie.service.UserService;

@RestController
@RequestMapping("/achei-e")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/user/{id}")
  public ResponseEntity<UserDTO> searchUserById(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.searchUserById(id));
  }

  @PutMapping("/user")
  public ResponseEntity<MessageUtil> updateUser(
      @RequestParam(value = "usuarioId") UUID idUsuario, @RequestBody UserDTO userDTO) {
    return ResponseEntity.status(HttpStatus.OK).body(new MessageUtil(
        this.userService.updateUser(idUsuario, userDTO).getMessage()));
  }

  @PutMapping("/user/nova-password")
  public ResponseEntity<MessageUtil> requestPasswordChange(@RequestBody EmailDTO emailDto) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new MessageUtil(this.userService.requestPasswordChange(emailDto.email())));
    }

    @PutMapping("user/redefinir-password")
    public ResponseEntity<MessageUtil> updatePassword(@RequestBody LoginRequest login){
      return ResponseEntity.status(HttpStatus.OK)
          .body(new MessageUtil(this.userService.updatePassword(login).getMessage()));
    }
}
