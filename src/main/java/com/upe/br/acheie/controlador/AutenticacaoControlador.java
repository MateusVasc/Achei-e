package com.upe.br.acheie.controlador;

import com.upe.br.acheie.dominio.dto.CadastroRequest;
import com.upe.br.acheie.dominio.dto.LoginRequest;
import com.upe.br.acheie.servico.AutenticacaoServico;
import com.upe.br.acheie.utils.MensagemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AutenticacaoControlador {

  private final AutenticacaoServico autenticacaoServico;
  private AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
    ResponseEntity<Object> response;

    try {
      response = ResponseEntity.ok(this.autenticacaoServico.autenticarLogin(request));
    } catch (Exception e) {
      response = ResponseEntity.badRequest()
          .body(new MensagemUtil(e.getMessage()));
    }

    return response;
  }

  @PostMapping("/cadastro")
  public ResponseEntity<Object> cadastrarUsuario(@RequestBody CadastroRequest request) {
    ResponseEntity<Object> response;

    try {
      response = ResponseEntity.ok(this.autenticacaoServico.cadastrarUsuario(request));
    } catch (Exception e) {
      response = ResponseEntity.badRequest()
          .body(new MensagemUtil(e.getMessage()));
    }

    return response;
  }
}
