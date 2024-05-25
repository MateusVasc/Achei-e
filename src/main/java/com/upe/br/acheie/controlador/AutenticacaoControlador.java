package com.upe.br.acheie.controlador;

import com.upe.br.acheie.dominio.dto.request.CadastroRequest;
import com.upe.br.acheie.dominio.dto.request.LoginRequest;
import com.upe.br.acheie.servico.AutenticacaoServico;
import com.upe.br.acheie.dominio.utils.MensagemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achei-e")
public class AutenticacaoControlador {

  private final AutenticacaoServico autenticacaoServico;
  private AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
    ResponseEntity<Object> response;

    try {
      response = ResponseEntity.ok(this.autenticacaoServico.loginUsuario(request));
    } catch (Exception e) {
      response = ResponseEntity.badRequest()
          .body(new MensagemUtil(e.getMessage()));
    }

    return response;
  }

  @PostMapping("/cadastro")
  public ResponseEntity<Object> cadastro(@RequestBody CadastroRequest request) {
    ResponseEntity<Object> response;

    try {
      response = ResponseEntity.ok(this.autenticacaoServico.cadastrarUsuario(request));
    } catch (Exception e) {
      response = ResponseEntity.badRequest()
          .body(new MensagemUtil(e.getMessage()));
    }

    return response;
  }

  @DeleteMapping("/excluir/{email}")
  public ResponseEntity<Object> excluirPorEmail(@PathVariable("email") String email) {
    ResponseEntity<Object> response;

    try {
      response = ResponseEntity.ok(this.autenticacaoServico.excluirUsuarioPorEmail(email));
    } catch (Exception e) {
      response = ResponseEntity.badRequest().body(new MensagemUtil(e.getMessage()));
    }

    return response;
  }
}
