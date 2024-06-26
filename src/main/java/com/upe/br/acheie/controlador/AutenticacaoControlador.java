package com.upe.br.acheie.controlador;

import com.upe.br.acheie.dominio.dto.request.CadastroRequest;
import com.upe.br.acheie.dominio.dto.request.LoginRequest;
import com.upe.br.acheie.servico.AutenticacaoServico;
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
public class AutenticacaoControlador {

  private final AutenticacaoServico autenticacaoServico;

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(this.autenticacaoServico.loginUsuario(request));
  }

  @PostMapping("/cadastro")
  public ResponseEntity<Object> cadastro(@RequestBody CadastroRequest request) {
    return ResponseEntity.ok(this.autenticacaoServico.cadastrarUsuario(request));
  }

  @DeleteMapping("/excluir/{email}")
  public ResponseEntity<Object> excluirPorEmail(@PathVariable("email") String email) {
    return ResponseEntity.ok(this.autenticacaoServico.excluirUsuarioPorEmail(email));
  }
}
