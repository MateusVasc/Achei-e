package com.upe.br.acheie.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HerokuControlador {

  @GetMapping("/")
  public String login() {
    return "Deu certo";
  }

  @GetMapping("/favicon.ico")
  public String loginFavicon() {
    return "Deu certo de novo";
  }

}
