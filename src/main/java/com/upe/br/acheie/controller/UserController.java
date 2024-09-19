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

  @GetMapping("/user/{id}")
  public ResponseEntity<UserDto> searchById(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(userService.searchUserById(id));
  }

  @PutMapping("/user")
  public ResponseEntity<UtilMessage> updateUser(
      @RequestParam(value = "userId") UUID userId, @RequestBody UserDto userDto) {
    return ResponseEntity.status(HttpStatus.OK).body(new UtilMessage(
        this.userService.updateUser(userId, userDto).getMensagem()));
  }

  @PutMapping("/user/new-password")
  public ResponseEntity<UtilMessage> requestChangePassword(@RequestBody EmailDto emailDto) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new UtilMessage(this.userService.requestChangePassword(emailDto.email())));
  }

  @PutMapping("user/update-password")
  public ResponseEntity<UtilMessage> updatePassword(@RequestBody LoginRequest login){
    return ResponseEntity.status(HttpStatus.OK)
        .body(new UtilMessage(this.userService.updatePassword(login).getMensagem()));
  }
}
