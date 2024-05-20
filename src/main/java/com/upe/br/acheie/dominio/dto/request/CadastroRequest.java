package com.upe.br.acheie.dominio.dto.request;

import com.upe.br.acheie.dominio.enums.Curso;
import com.upe.br.acheie.dominio.enums.Periodo;
import java.util.Date;

public record CadastroRequest(String nome, String sobrenome, String email, String senha,
                              Curso curso, Periodo periodo, String telefone,
                              byte[] foto, Date criacaoDaConta) {

}
