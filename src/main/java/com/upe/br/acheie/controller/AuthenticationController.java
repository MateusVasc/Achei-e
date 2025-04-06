package com.upe.br.acheie.controller;

import com.upe.br.acheie.dtos.request.RegisterRequest;
import com.upe.br.acheie.dtos.request.LoginRequest;
import com.upe.br.acheie.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achei-e")
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(this.authenticationService.loginUser(request));
  }

  @PostMapping("/cadastro")
  public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(this.authenticationService.registerUser(request));
  }

  @DeleteMapping("/excluir/{email}")
  public ResponseEntity<Object> deleteByEmail(@PathVariable("email") String email) {
    return ResponseEntity.ok(this.authenticationService.excluirUsuarioPorEmail(email));
  }
}
